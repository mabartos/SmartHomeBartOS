package org.mabartos.service.core;

import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.UserModel;

import java.util.Set;

public interface HomeService extends CRUDServiceChild<HomeModel, UserModel> {

    boolean addDeviceToHome(DeviceModel device, Long homeID);

    boolean removeDeviceFromHome(DeviceModel device, Long homeID);

    Set<DeviceModel> getAllUnAssignedDevices(Long homeID);
}
