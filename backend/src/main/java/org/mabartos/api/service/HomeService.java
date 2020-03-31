package org.mabartos.api.service;

import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;

import java.util.Set;
import java.util.UUID;

public interface HomeService extends CRUDService<HomeModel, Long> {

    boolean addDeviceToHome(DeviceModel device, Long homeID);

    HomeModel addUserToHome(UUID userID, Long homeID);

    boolean removeDeviceFromHome(DeviceModel device, Long homeID);

    Set<DeviceModel> getAllUnAssignedDevices(Long homeID);

    HomeModel updateFromJson(Long homeID, String JSON);
}
