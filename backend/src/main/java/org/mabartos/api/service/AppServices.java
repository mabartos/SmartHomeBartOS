package org.mabartos.api.service;

import io.vertx.mutiny.core.Vertx;
import org.mabartos.api.model.MqttClientService;
import org.mabartos.api.service.invitations.HomeInvitationService;

import javax.persistence.EntityManager;

public interface AppServices {

    <T> T getProvider(Class<T> clazz);

    EntityManager getEntityManager();

    UserService users();

    HomeService homes();

    RoomService rooms();

    DeviceService devices();

    CapabilityService capabilities();

    HomeInvitationService invitations();

    MqttClientService mqttClientsRepresentation();

    Vertx getVertx();
}
