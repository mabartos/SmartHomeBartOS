package org.mabartos.services;

import org.mabartos.api.model.BartSession;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.CapabilityService;
import org.mabartos.api.service.DeviceService;
import org.mabartos.api.service.HomeService;
import org.mabartos.api.service.RoomService;
import org.mabartos.api.service.UserService;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.services.core.UserServiceImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
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

    @Inject
    BartMqttClient mqttClient;

    @PersistenceContext
    EntityManager entityManager;

    public static Logger logger = Logger.getLogger(DefaultBartSession.class.getName());
    private Map<Class, Object> providers = new HashMap<>();
    private HomeModel actualHome;
    private Long actualHomeID;

    public DefaultBartSession() {
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
    public void setMqttClient(BartMqttClient mqttClient) {
        this.mqttClient = mqttClient;
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