package org.mabartos.api.service.home;

import org.mabartos.api.service.AbleSendMQTT;
import org.mabartos.api.service.CRUDService;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.home.HomeModel;

import java.util.Set;
import java.util.UUID;

public interface HomeService extends CRUDService<HomeModel, Long>, AbleSendMQTT {

    HomeInvitationService invitations();

    boolean addDeviceToHome(DeviceModel device, Long homeID);

    HomeModel addUserToHome(UUID userID, Long homeID);

    boolean removeDeviceFromHome(DeviceModel device, Long homeID);

    Set<DeviceModel> getAllUnAssignedDevices(Long homeID);

    HomeModel updateFromJson(Long homeID, String JSON);
}
