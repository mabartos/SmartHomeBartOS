/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.device;


import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.data.general.device.DeviceData;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.protocol.mqtt.AbleSendMQTT;
import org.mabartos.api.service.CRUDService;

import java.util.Set;

public interface DeviceService extends CRUDService<DeviceModel, Long>, AbleSendMQTT {

    Set<DeviceModel> findByType(CapabilityType type);

    DeviceModel addDeviceToRoom(Long roomID, Long deviceID);

    int deleteAllFromHome(Long homeID);

    DeviceModel updateFromJson(Long ID, String JSON);

    boolean removeDeviceFromRoom(Long roomID, Long deviceID);

    DeviceModel fromDataToModel(DeviceData data);
}
