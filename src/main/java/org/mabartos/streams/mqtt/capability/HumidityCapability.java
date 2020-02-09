package org.mabartos.streams.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.capability.HumidityDevModel;
import org.mabartos.service.core.DeviceService;
import org.mabartos.streams.mqtt.BarMqttClient;

public class HumidityCapability extends GeneralMqttCapability<HumidityDevModel> {

    public HumidityCapability(BarMqttClient client, DeviceService deviceService, Long id, MqttMessage message) {
        super(client, deviceService, CapabilityType.HUMIDITY, id, message);
    }
    
}
