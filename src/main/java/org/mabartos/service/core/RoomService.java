package org.mabartos.service.core;

import org.mabartos.general.RoomType;
import org.mabartos.persistence.model.RoomModel;

import java.util.List;

public interface RoomService extends CRUDService<RoomModel> {

    List<RoomModel> findByType(RoomType type);
}
