package org.mabartos.api.service;

import org.mabartos.general.RoomType;
import org.mabartos.persistence.model.RoomModel;

import java.util.Set;

public interface RoomService extends CRUDService<RoomModel> {

    Set<RoomModel> findByType(RoomType type);
}
