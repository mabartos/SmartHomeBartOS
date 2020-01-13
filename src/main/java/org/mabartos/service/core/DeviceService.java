package org.mabartos.service.core;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.DeviceModel;

import java.util.List;

public interface DeviceService extends CRUDService<DeviceModel> {

    List<DeviceModel> findByType(DeviceType type);
}
