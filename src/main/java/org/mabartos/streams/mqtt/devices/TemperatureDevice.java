package org.mabartos.streams.mqtt.devices;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.devices.TemperatureDevModel;
import org.mabartos.service.core.DeviceService;
import org.mabartos.streams.mqtt.BarMqttClient;

public class TemperatureDevice extends GeneralMqttDevice<TemperatureDevModel> {

    public TemperatureDevice(BarMqttClient client, DeviceService deviceService, Long id, MqttMessage message) {
        super(client, deviceService, DeviceType.TEMPERATURE, id, message);

    }

}
