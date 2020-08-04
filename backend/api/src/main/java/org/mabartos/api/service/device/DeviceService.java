/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.device;


import org.mabartos.api.data.general.device.DeviceData;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.protocol.mqtt.AbleSendMQTT;
import org.mabartos.api.service.CRUDService;

public interface DeviceService extends CRUDService<DeviceModel, Long>, AbleSendMQTT {

    DeviceModel addDeviceToRoom(Long roomID, Long deviceID);

    int deleteAllFromHome(Long homeID);

    DeviceModel updateFromJson(Long ID, String JSON);

    boolean removeDeviceFromRoom(Long roomID, Long deviceID);

    DeviceModel fromDataToModel(DeviceData data);
}
