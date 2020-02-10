package org.mabartos.service.core;

import org.mabartos.general.RoomType;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.RoomModel;

import java.util.Set;

public interface RoomService extends CRUDServiceChild<RoomModel, HomeModel> {

    Set<RoomModel> findByType(RoomType type);
}
