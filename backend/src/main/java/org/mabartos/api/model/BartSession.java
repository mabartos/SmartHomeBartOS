package org.mabartos.api.model;

import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.CapabilityService;
import org.mabartos.api.service.DeviceService;
import org.mabartos.api.service.HomeService;
import org.mabartos.api.service.RoomService;
import org.mabartos.api.service.UserService;
import org.mabartos.persistence.model.HomeModel;

import javax.persistence.EntityManager;

public interface BartSession {

    HomeModel getActualHome();

    void setActualHome(Long id);

    <T> T getProvider(Class<T> clazz);

    EntityManager getEntityManager();

    BartMqttClient getMqttClient();

    void setMqttClient(BartMqttClient mqttClient);

    UserService users();

    HomeService homes();

    RoomService rooms();

    DeviceService devices();

    CapabilityService capabilities();
}
