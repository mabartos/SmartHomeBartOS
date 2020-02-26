package org.mabartos.api.service;

import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;

import java.util.Set;

public interface HomeService extends CRUDService<HomeModel> {

    boolean addDeviceToHome(DeviceModel device, Long homeID);

    boolean addUserToHome(Long userID, Long homeID);

    boolean removeDeviceFromHome(DeviceModel device, Long homeID);

    Set<DeviceModel> getAllUnAssignedDevices(Long homeID);
}
