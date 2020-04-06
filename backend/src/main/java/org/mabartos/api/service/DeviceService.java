package org.mabartos.api.service;

import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.DeviceModel;

import java.util.Set;

public interface DeviceService extends CRUDService<DeviceModel, Long> {

    Set<DeviceModel> findByType(CapabilityType type);

    DeviceModel addDeviceToRoom(Long roomID, Long deviceID);

    boolean removeDeviceFromRoom(Long roomID, Long deviceID);
}
