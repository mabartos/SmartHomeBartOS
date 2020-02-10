package org.mabartos.streams.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.persistence.model.capability.LightCapModel;
import org.mabartos.service.core.CapabilityService;
import org.mabartos.streams.mqtt.BarMqttClient;
import org.mabartos.streams.mqtt.topics.CapabilityTopic;

public class LightCapability extends GeneralMqttCapability<LightCapModel> {

    public LightCapability(BarMqttClient client, CapabilityService capabilityService, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(client, capabilityService, capabilityTopic, message);
    }
}
