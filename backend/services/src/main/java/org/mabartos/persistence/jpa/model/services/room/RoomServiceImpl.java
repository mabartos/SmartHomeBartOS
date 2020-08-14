/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.room;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.controller.room.RoomData;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.protocol.mqtt.BartMqttClient;
import org.mabartos.api.protocol.mqtt.MqttClientManager;
import org.mabartos.api.protocol.mqtt.TopicUtils;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.room.RoomService;
import org.mabartos.persistence.jpa.repository.RoomRepository;
import org.mabartos.services.model.CRUDServiceImpl;
import org.mabartos.services.utils.DataToModelBase;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class RoomServiceImpl extends CRUDServiceImpl<RoomModel, RoomEntity, RoomRepository, Long> implements RoomService {

    private AppServices services;

    @Inject
    RoomServiceImpl(RoomRepository repository, AppServices services) {
        super(repository);
        this.services = services;
    }

    public void start(@Observes StartupEvent event) {
    }

    @Override
    public Set<RoomModel> getAll() {
        Query query = getEntityManager().createNamedQuery("getAllRooms", RoomModel.class);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public RoomModel findByID(Long id) {
        Query query = getEntityManager().createNamedQuery("getRoomByID", RoomModel.class);
        query.setParameter("id", id);
        return (RoomModel) query.getSingleResult();
    }

    @Override
    public int deleteAllFromHome(Long homeID) {
        getEntityManager().flush();
        getEntityManager().clear();

        Query query = entityManager.createNamedQuery("deleteRoomsFromHome");
        query.setParameter("homeID", homeID);
        return query.executeUpdate();
    }

    @Override
    public Set<UserModel> getOwners(Long roomID) {
        RoomModel room = getRepository().findById(roomID);
        if (room != null && room.getOwnersID() != null) {
            Set<UUID> ownersID = room.getOwnersID();
            Set<UserModel> owners = new HashSet<>();
            ownersID.forEach(id -> owners.add(services.users().findByID(id)));
            return owners.stream().filter(Objects::nonNull).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public boolean addOwnerByID(Long roomID, UUID id) {
        RoomModel room = getRepository().findById(roomID);
        return room != null && room.addOwnerID(id);
    }

    @Override
    public boolean removeOwner(Long roomID, UUID id) {
        RoomModel room = getRepository().findById(roomID);
        return room != null && room.removeOwnerID(id);
    }

    @Override
    public boolean isOwner(Long roomID, UUID id) {
        RoomModel room = getRepository().findById(roomID);
        return room != null && room.isOwner(id);
    }

    @Override
    public RoomModel updateFromJson(Long roomID, String JSON) {
        RoomModel room = getRepository().findById(roomID);
        if (room != null) {
            room = DataToModelBase.toRoomModel(RoomData.fromJson(JSON), room);
            return updateByID(roomID, room);
        }
        return null;
    }

    @Override
    public boolean deleteByID(Long id) {
        Query query = entityManager.createNamedQuery("setDeviceRoomToNull");
        query.setParameter("roomID", id);
        query.executeUpdate();

        clearRetainedMessages(id);

        getEntityManager().flush();
        getEntityManager().clear();

        query = entityManager.createNamedQuery("deleteTriggersByRoomID");
        query.setParameter("roomID", id);
        query.executeUpdate();

        query = entityManager.createNamedQuery("deleteRoomByID");
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }

    @Override
    public void clearRetainedMessages(Long id) {
        RoomModel room = findByID(id);
        if (room != null && room.getHome() != null) {
            BartMqttClient client = services.mqttManager().getMqttForHome(room.getHomeID());
            MqttClientManager.clearRetainedMessages(client, TopicUtils.getRoomTopic(room.getHomeID(), id));
            room.getChildren().forEach(dev -> services.devices().clearRetainedMessages(dev.getID()));
        }
    }
}
