package org.mabartos.services;

import org.mabartos.api.model.BartSession;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.CapabilityService;
import org.mabartos.api.service.DeviceService;
import org.mabartos.api.service.HomeService;
import org.mabartos.api.service.RoomService;
import org.mabartos.api.service.UserService;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.model.UserModel;
import org.mabartos.services.core.UserServiceImpl;

import javax.enterprise.context.RequestScoped;
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

@RequestScoped
public class DefaultBartSession implements BartSession {

    @Inject
    BeanManager beanManager;

    @Inject
    BartMqttClient mqttClient;

    @PersistenceContext
    EntityManager entityManager;

    public static Logger logger = Logger.getLogger(DefaultBartSession.class.getName());
    private Map<Class, Object> providers = new HashMap<>();

    private UserModel actualUser;
    private Long actualUserID;

    protected HomeModel actualHome;
    protected Long actualHomeID;

    private RoomModel actualRoom;
    private Long actualRoomID;

    private DeviceModel actualDevice;
    private Long actualDeviceID;

    public DefaultBartSession() {
    }

    @Override
    public UserModel getActualUser() {
        return actualUser != null ? actualUser : getProvider(UserService.class).findByID(actualUserID);
    }

    @Override
    public BartSession setActualUser(Long id) {
        this.actualUser = getProvider(UserService.class).findByID(id);
        this.actualUserID = id;
        return this;
    }

    @Override
    public HomeModel getActualHome() {
        return actualHome != null ? actualHome : getProvider(HomeService.class).findByID(actualHomeID);
    }

    @Override
    public BartSession setActualHome(Long id) {
        this.actualHome = getProvider(HomeService.class).findByID(id);
        this.actualHomeID = id;
        return this;
    }

    @Override
    public RoomModel getActualRoom() {
        return actualRoom != null ? actualRoom : getProvider(RoomService.class).findByID(actualRoomID);
    }

    @Override
    public BartSession setActualRoom(Long id) {
        this.actualRoom = getProvider(RoomService.class).findByID(id);
        this.actualRoomID = id;
        return this;
    }

    @Override
    public DeviceModel getActualDevice() {
        return actualDevice != null ? actualDevice : getProvider(DeviceService.class).findByID(actualDeviceID);
    }

    @Override
    public BartSession setActualDevice(Long id) {
        this.actualDevice = getProvider(DeviceService.class).findByID(id);
        this.actualDeviceID = id;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getProvider(Class<T> clazz) {
        return (T) providers.computeIfAbsent(clazz, item -> {
            Set<Bean<?>> beans = beanManager.getBeans(clazz);
            if (beans.iterator().hasNext()) {
                Bean<?> bean = beans.iterator().next();
                CreationalContext<?> context = beanManager.createCreationalContext(bean);
                return beanManager.getReference(bean, clazz, context);
            }
            return null;
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
    public BartSession setMqttClient(BartMqttClient mqttClient) {
        this.mqttClient = mqttClient;
        return this;
    }

    @Override
    public UserService users() {
        return getProvider(UserServiceImpl.class);
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