package org.mabartos.streams.mqtt.devices;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.devices.HumidityDevModel;
import org.mabartos.service.core.DeviceService;
import org.mabartos.streams.mqtt.BarMqttClient;

public class HumidityDevice extends GeneralMqttDevice<HumidityDevModel> {

    public HumidityDevice(BarMqttClient client, DeviceService deviceService, Long id, MqttMessage message) {
        super(client, deviceService, CapabilityType.HUMIDITY, id, message);
    }

    
}
