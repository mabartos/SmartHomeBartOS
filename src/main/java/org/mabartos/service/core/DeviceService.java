package org.mabartos.service.core;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.RoomModel;

import java.util.List;

public interface DeviceService extends CRUDServiceChild<DeviceModel, RoomModel> {

    List<DeviceModel> findByType(DeviceType type);
}
