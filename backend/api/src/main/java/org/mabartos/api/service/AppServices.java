/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service;

import org.mabartos.api.protocol.mqtt.MqttClientManager;
import org.mabartos.api.service.auth.AuthService;
import org.mabartos.api.service.capability.CapabilityService;
import org.mabartos.api.service.device.DeviceService;
import org.mabartos.api.service.home.HomeService;
import org.mabartos.api.service.room.RoomService;
import org.mabartos.api.service.user.UserService;

import javax.persistence.EntityManager;

public interface AppServices {

    <T> T getProvider(Class<T> clazz);

    EntityManager getEntityManager();

    AuthService auth();

    UserService users();

    HomeService homes();

    RoomService rooms();

    DeviceService devices();

    CapabilityService capabilities();

    MqttClientManager mqttManager();
}
