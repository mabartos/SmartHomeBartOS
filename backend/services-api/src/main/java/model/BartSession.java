package model;

import models.HomeModel;
import protocol.BartMqttClient;
import service.CapabilityService;
import service.DeviceService;
import service.HomeService;
import service.RoomService;
import service.UserService;

import javax.persistence.EntityManager;

public interface BartSession {

    HomeModel getActualHome();

    void setActualHome(Long id);

    <T> T getProvider(Class<T> clazz);

    EntityManager getEntityManager();

    BartMqttClient getMqttClient();

    UserService users();

    HomeService homes();

    RoomService rooms();

    DeviceService devices();

    CapabilityService capabilities();
}
