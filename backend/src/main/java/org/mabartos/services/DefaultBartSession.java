package org.mabartos.services;

import org.mabartos.api.model.BartSession;
import org.mabartos.api.protocol.MqttClientManager;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.CapabilityService;
import org.mabartos.api.service.DeviceService;
import org.mabartos.api.service.HomeService;
import org.mabartos.api.service.RoomService;
import org.mabartos.api.service.UserService;
import org.mabartos.api.service.auth.AuthService;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.model.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

@RequestScoped
public class DefaultBartSession implements BartSession {
    public static Logger logger = Logger.getLogger(DefaultBartSession.class.getName());

    @Inject
    AppServices services;

    @Inject
    AuthService authService;

    @Inject
    MqttClientManager mqttClientManager;


    private UserModel actualUser;
    private Long actualUserID;

    protected HomeModel actualHome;
    protected Long actualHomeID;

    private RoomModel actualRoom;
    private Long actualRoomID;

    private DeviceModel actualDevice;
    private Long actualDeviceID;

    private CapabilityModel actualCapability;
    private Long actualCapabilityID;

    @Inject
    public DefaultBartSession() {
    }

    @Override
    public UserModel getActualUser() {
        return actualUser != null ? actualUser : services().getProvider(UserService.class).findByID(actualUserID);
    }

    @Override
    public BartSession setActualUser(Long id) {
        this.actualUser = services().getProvider(UserService.class).findByID(id);
        this.actualUserID = id;
        return this;
    }

    @Override
    public HomeModel getActualHome() {
        return actualHome != null ? actualHome : services().getProvider(HomeService.class).findByID(actualHomeID);
    }

    @Override
    public BartSession setActualHome(Long id) {
        this.actualHome = services().getProvider(HomeService.class).findByID(id);
        this.actualHomeID = id;
        return this;
    }

    @Override
    public RoomModel getActualRoom() {
        return actualRoom != null ? actualRoom : services().getProvider(RoomService.class).findByID(actualRoomID);
    }

    @Override
    public BartSession setActualRoom(Long id) {
        this.actualRoom = services().getProvider(RoomService.class).findByID(id);
        this.actualRoomID = id;
        return this;
    }

    @Override
    public DeviceModel getActualDevice() {
        return actualDevice != null ? actualDevice : services().getProvider(DeviceService.class).findByID(actualDeviceID);
    }

    @Override
    public BartSession setActualDevice(Long id) {
        this.actualDevice = services().getProvider(DeviceService.class).findByID(id);
        this.actualDeviceID = id;
        return this;
    }

    @Override
    public CapabilityModel getActualCapability() {
        return actualCapability != null ? actualCapability : services().getProvider(CapabilityService.class).findByID(actualCapabilityID);
    }

    @Override
    public BartSession setActualCapability(Long id) {
        this.actualCapability = services().getProvider(CapabilityService.class).findByID(id);
        this.actualCapabilityID = id;
        return this;
    }

    @Override
    public MqttClientManager getClientManager() {
        return mqttClientManager;
    }

    @Override
    public AppServices services() {
        return services;
    }

    @Override
    public AuthService auth() {
        return authService;
    }
}