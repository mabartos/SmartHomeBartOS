package org.mabartos.api.model;

import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.CapabilityService;
import org.mabartos.api.service.DeviceService;
import org.mabartos.api.service.HomeService;
import org.mabartos.api.service.RoomService;
import org.mabartos.api.service.UserService;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.model.UserModel;

import javax.persistence.EntityManager;

public interface BartSession {

    UserModel getActualUser();

    BartSession setActualUser(Long id);

    HomeModel getActualHome();

    BartSession setActualHome(Long id);

    RoomModel getActualRoom();

    BartSession setActualRoom(Long id);

    DeviceModel getActualDevice();

    BartSession setActualDevice(Long id);

    CapabilityModel getActualCapability();

    BartSession setActualCapability(Long id);

    <T> T getProvider(Class<T> clazz);

    EntityManager getEntityManager();

    BartMqttClient getMqttClient();

    BartSession setMqttClient(BartMqttClient mqttClient);

    UserService users();

    HomeService homes();

    RoomService rooms();

    DeviceService devices();

    CapabilityService capabilities();
}
