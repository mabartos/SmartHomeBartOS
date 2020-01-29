package org.mabartos.streams.mqtt.devices;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.devices.HumidityDevModel;
import org.mabartos.service.core.DeviceService;

public class HumidityDevice extends GeneralMqttDevice<HumidityDevModel> {

    public HumidityDevice(DeviceService deviceService, Long id, String message) {
        super(deviceService, DeviceType.HUMIDITY, id, message);
    }

    
}
