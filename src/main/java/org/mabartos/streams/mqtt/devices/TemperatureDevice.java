package org.mabartos.streams.mqtt.devices;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.devices.TemperatureDevModel;
import org.mabartos.service.core.DeviceService;

public class TemperatureDevice extends GeneralMqttDevice<TemperatureDevModel> {

    public TemperatureDevice(DeviceService deviceService, Long id, String message) {
        super(deviceService, DeviceType.TEMPERATURE, id, message);
    }

}
