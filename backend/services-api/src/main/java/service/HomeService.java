package service;

import models.DeviceModel;
import models.HomeModel;
import models.UserModel;

import javax.decorator.Decorator;
import java.util.Set;

@Decorator
public interface HomeService extends CRUDServiceChild<HomeModel, UserModel> {

    boolean addDeviceToHome(DeviceModel device, Long homeID);

    boolean removeDeviceFromHome(DeviceModel device, Long homeID);

    Set<DeviceModel> getAllUnAssignedDevices(Long homeID);
}
