package org.mabartos.api.service;

import io.vertx.mutiny.core.Vertx;
import org.mabartos.api.model.MqttClientService;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.protocol.MqttClientManager;
import org.mabartos.api.service.auth.AuthService;
import org.mabartos.api.service.home.HomeService;
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

    Vertx getVertx();
}
