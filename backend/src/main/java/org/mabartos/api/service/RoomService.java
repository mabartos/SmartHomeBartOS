package org.mabartos.api.service;

import org.mabartos.general.RoomType;
import org.mabartos.persistence.model.room.RoomModel;
import org.mabartos.persistence.model.user.UserModel;

import java.util.Set;
import java.util.UUID;

public interface RoomService extends CRUDService<RoomModel, Long> {

    Set<RoomModel> findByType(RoomType type);

    int deleteAllFromHome(Long homeID);

    Set<UserModel> getOwners(Long roomID);

    boolean addOwnerByID(Long roomID, UUID id);

    boolean removeOwner(Long roomID, UUID id);

    boolean isOwner(Long roomID, UUID id);

    RoomModel updateFromJson(Long roomID, String JSON);
}
