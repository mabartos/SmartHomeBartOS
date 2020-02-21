package service;

import models.DeviceModel;
import models.RoomModel;
import models.capability.utils.CapabilityType;

import java.util.Set;

public interface DeviceService extends CRUDServiceChild<DeviceModel, RoomModel> {

    Set<DeviceModel> findByType(CapabilityType type);

}
