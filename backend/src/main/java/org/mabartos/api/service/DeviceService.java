package org.mabartos.api.service;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.RoomModel;

import java.util.Set;

public interface DeviceService extends CRUDService<DeviceModel> {

    Set<DeviceModel> findByType(CapabilityType type);
}
