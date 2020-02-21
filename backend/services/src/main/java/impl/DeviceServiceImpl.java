package impl;

import exceptions.DeviceConflictException;
import models.DeviceModel;
import models.RoomModel;
import models.capability.utils.CapabilityType;
import repository.DeviceRepository;
import service.DeviceService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

@Dependent
public class DeviceServiceImpl extends CRUDServiceChildImpl<DeviceModel, DeviceRepository, RoomModel> implements DeviceService {

    @Inject
    DeviceServiceImpl() {
        super(DeviceRepository.class);
    }

    @Override
    public DeviceModel create(DeviceModel entity) {
        if (!isDeviceInHome(entity))
            return super.create(entity);
        else throw new DeviceConflictException();
    }

    @Override
    public Set<DeviceModel> findByType(CapabilityType type) {
        return getRepository().find("type", type).stream().collect(Collectors.toSet());
    }

    private boolean isDeviceInHome(DeviceModel device) {
        return DeviceModel.find("name", device.getName()).count() > 0;
    }
}
