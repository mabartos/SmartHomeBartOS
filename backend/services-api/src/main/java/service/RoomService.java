package service;

import models.HomeModel;
import models.RoomModel;
import utils.RoomType;

import java.util.Set;

public interface RoomService extends CRUDServiceChild<RoomModel, HomeModel> {

    Set<RoomModel> findByType(RoomType type);
}
