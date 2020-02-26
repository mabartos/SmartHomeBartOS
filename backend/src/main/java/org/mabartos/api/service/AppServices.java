package org.mabartos.api.service;

import org.mabartos.api.model.MqttClientService;

import javax.persistence.EntityManager;

public interface AppServices {

    <T> T getProvider(Class<T> clazz);

    EntityManager getEntityManager();

    UserService users();

    HomeService homes();

    RoomService rooms();

    DeviceService devices();

    CapabilityService capabilities();

    MqttClientService mqttClientsRepresentation();
}
