package org.mabartos.streams.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.persistence.model.capability.HumidityCapModel;
import org.mabartos.service.core.CapabilityService;
import org.mabartos.streams.mqtt.BarMqttClient;
import org.mabartos.streams.mqtt.topics.CapabilityTopic;

public class HumidityCapability extends GeneralMqttCapability<HumidityCapModel> {

    public HumidityCapability(BarMqttClient client, CapabilityService capabilityService, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(client, capabilityService, capabilityTopic, message);
    }

}
