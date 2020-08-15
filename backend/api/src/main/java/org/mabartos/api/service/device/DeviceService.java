/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.device;


import org.mabartos.api.data.general.device.ConnectData;
import org.mabartos.api.data.general.device.DeviceData;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.protocol.mqtt.AbleSendMQTT;
import org.mabartos.api.service.CRUDService;

public interface DeviceService extends CRUDService<DeviceModel, Long>, AbleSendMQTT {

    DeviceModel addDeviceToRoom(Long roomID, Long deviceID);

    boolean removeDeviceFromRoom(Long roomID, Long deviceID);

    DeviceData createToHomeJSON(DeviceData data, Long homeID);

    DeviceData createToRoomJSON(DeviceData data, Long roomID);

    ConnectData connectDevice(Long deviceID);

    boolean disconnectDevice(Long deviceID);

    Long getRoomID(Long deviceID);

    int deleteAllFromHome(Long homeID);

    DeviceModel updateFromJson(Long ID, String JSON);

    DeviceModel fromDataToModel(DeviceData data);
}
