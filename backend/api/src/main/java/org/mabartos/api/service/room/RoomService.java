/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.room;

import org.mabartos.api.model.room.RoomModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.protocol.mqtt.AbleSendMQTT;
import org.mabartos.api.service.CRUDService;

import java.util.Set;
import java.util.UUID;

public interface RoomService extends CRUDService<RoomModel, Long>, AbleSendMQTT {

    int deleteAllFromHome(Long homeID);

    Set<UserModel> getOwners(Long roomID);

    boolean addOwnerByID(Long roomID, UUID id);

    boolean removeOwner(Long roomID, UUID id);

    boolean isOwner(Long roomID, UUID id);

    RoomModel updateFromJson(Long roomID, String JSON);
}
