package org.mabartos.streams.mqtt.devices;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.devices.LightDevModel;
import org.mabartos.service.core.DeviceService;
import org.mabartos.streams.mqtt.BarMqttClient;

public class LightDevice extends GeneralMqttDevice<LightDevModel> {

    public LightDevice(BarMqttClient client, DeviceService deviceService, Long id, MqttMessage message) {
        super(client, deviceService, DeviceType.LIGHT, id, message);
    }
}
