package org.mabartos.service.impl;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.repository.DeviceRepository;
import org.mabartos.service.core.DeviceService;

import java.util.List;

public class DeviceServiceImpl extends CRUDServiceImpl<DeviceModel, DeviceRepository> implements DeviceService {


    DeviceServiceImpl(DeviceRepository repository) {
        super(repository);
    }

    @Override
    public List<DeviceModel> findByType(DeviceType type) {
        return getRepository().find("type", type).list();

    }
}
