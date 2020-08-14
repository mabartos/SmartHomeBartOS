/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller;

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

import java.io.Serializable;
import java.util.UUID;

public interface BartSession extends Serializable {

    UserModel getActualUser();

    BartSession setActualUser(UUID id);

    HomeModel getActualHome();

    BartSession setActualHome(Long id);

    RoomModel getActualRoom();

    BartSession setActualRoom(Long id);

    DeviceModel getActualDevice();

    BartSession setActualDevice(Long id);

    CapabilityModel getActualCapability();

    BartSession setActualCapability(Long id);

    HomeInvitationModel getActualInvitation();

    BartSession setActualInvitation(Long id);

    TriggerModel getActualTrigger();

    BartSession setActualTrigger(Long id);

    MqttClientManager getClientManager();

    AppServices services();

    AuthService auth();
}
