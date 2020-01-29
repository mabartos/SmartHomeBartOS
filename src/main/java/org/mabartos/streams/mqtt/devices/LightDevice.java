package org.mabartos.streams.mqtt.devices;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.devices.LightDevModel;
import org.mabartos.service.core.DeviceService;

public class LightDevice extends GeneralMqttDevice<LightDevModel> {

    public LightDevice(DeviceService deviceService, Long id, String message) {
        super(deviceService, DeviceType.LIGHT, id, message);
    }
}
