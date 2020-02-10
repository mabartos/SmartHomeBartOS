package org.mabartos.service.core;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.RoomModel;

import java.util.Set;

public interface DeviceService extends CRUDServiceChild<DeviceModel, RoomModel> {

    Set<DeviceModel> findByType(CapabilityType type);
}
