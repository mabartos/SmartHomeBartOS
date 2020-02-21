import io.quarkus.runtime.StartupEvent;
import model.BartSession;
import models.HomeModel;
import mqtt.DefaultBartMqttClient;
import protocol.BartMqttClient;
import service.CapabilityService;
import service.DeviceService;
import service.HomeService;
import service.RoomService;
import service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@ApplicationScoped
public class DefaultBartSession implements BartSession {

    @Inject
    BeanManager beanManager;

    @PersistenceContext
    EntityManager entityManager;

    public static Logger logger = Logger.getLogger(DefaultBartSession.class.getName());
    private Map<Class, Object> providers = new HashMap<>();
    private HomeModel actualHome;
    private Long actualHomeID;
    BartMqttClient mqttClient;

    public DefaultBartSession() {
        this.mqttClient = getProvider(DefaultBartMqttClient.class);
    }

    void onStartup(@Observes StartupEvent event) {
        logger.info("Initialized BartSession");
    }

    @Override
    public HomeModel getActualHome() {
        return actualHome != null ? actualHome : getProvider(HomeService.class).findByID(actualHomeID);
    }

    @Override
    public void setActualHome(Long id) {
        this.actualHome = getProvider(HomeService.class).findByID(id);
        this.actualHomeID = id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getProvider(Class<T> clazz) {
        return (T) providers.computeIfAbsent(clazz, item -> {
            Set<Bean<?>> beans = beanManager.getBeans(clazz);
            Bean<?> bean = beans.iterator().next();
            CreationalContext<?> context = beanManager.createCreationalContext(bean);
            return beanManager.getReference(bean, clazz, context);
        });
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public BartMqttClient getMqttClient() {
        return mqttClient;
    }

    @Override
    public UserService users() {
        return getProvider(UserService.class);
    }

    @Override
    public HomeService homes() {
        return getProvider(HomeService.class);
    }

    @Override
    public RoomService rooms() {
        return getProvider(RoomService.class);
    }

    @Override
    public DeviceService devices() {
        return getProvider(DeviceService.class);
    }

    @Override
    public CapabilityService capabilities() {
        return getProvider(CapabilityService.class);
    }
}
