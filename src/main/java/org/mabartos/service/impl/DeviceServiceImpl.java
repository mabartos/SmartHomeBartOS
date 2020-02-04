package org.mabartos.service.impl;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.repository.DeviceRepository;
import org.mabartos.service.core.DeviceService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

@Dependent
public class DeviceServiceImpl extends CRUDServiceChildImpl<DeviceModel, DeviceRepository, RoomModel> implements DeviceService {

    @Inject
    DeviceServiceImpl(DeviceRepository repository) {
        super(repository);
    }

    @Override
    public DeviceModel create(DeviceModel entity) {
        if (isDeviceInHome(entity))
            return super.create(entity);
        return null;
    }

    @Override
    public Set<DeviceModel> findByType(DeviceType type) {
        return getRepository().find("type", type).stream().collect(Collectors.toSet());
    }

    private boolean isDeviceInHome(DeviceModel device) {
        return DeviceModel.find("name", device.getName()).count() == 0;
    }
}
