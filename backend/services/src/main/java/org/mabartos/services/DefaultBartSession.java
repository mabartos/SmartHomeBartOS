/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.services;

import org.mabartos.api.controller.BartSession;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.events.trigger.TriggerModel;
import org.mabartos.api.model.home.HomeInvitationModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.protocol.mqtt.MqttClientManager;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.auth.AuthService;
import org.mabartos.api.service.capability.CapabilityService;
import org.mabartos.api.service.capability.triggers.TriggerService;
import org.mabartos.api.service.device.DeviceService;
import org.mabartos.api.service.home.HomeInvitationService;
import org.mabartos.api.service.home.HomeService;
import org.mabartos.api.service.room.RoomService;
import org.mabartos.api.service.user.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.UUID;
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
    private UUID actualUserID;

    protected HomeModel actualHome;
    protected Long actualHomeID;

    private RoomModel actualRoom;
    private Long actualRoomID;

    private DeviceModel actualDevice;
    private Long actualDeviceID;

    private CapabilityModel actualCapability;
    private Long actualCapabilityID;

    private HomeInvitationModel actualInvitation;
    private Long actualInvitationID;

    private TriggerModel actualTrigger;
    private Long actualTriggerID;

    @Inject
    public DefaultBartSession() {
    }

    @Override
    public UserModel getActualUser() {
        return actualUser != null ? actualUser : services().getProvider(UserService.class).findByID(actualUserID);
    }

    @Override
    public BartSession setActualUser(UUID id) {
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
    public HomeInvitationModel getActualInvitation() {
        return (actualInvitation != null) ? actualInvitation : services().getProvider(HomeInvitationService.class).findByID(actualInvitationID);
    }

    @Override
    public BartSession setActualInvitation(Long id) {
        this.actualInvitation = services.getProvider(HomeInvitationService.class).findByID(id);
        this.actualInvitationID = id;
        return this;
    }

    @Override
    public TriggerModel getActualTrigger() {
        return (actualTrigger != null) ? actualTrigger : services().getProvider(TriggerService.class).findByID(actualTriggerID);
    }

    @Override
    public BartSession setActualTrigger(Long id) {
        this.actualTrigger = services().getProvider(TriggerService.class).findByID(id);
        this.actualTriggerID = id;
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