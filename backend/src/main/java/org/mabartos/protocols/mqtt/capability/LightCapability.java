package org.mabartos.protocols.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.persistence.model.capability.LightCapModel;
import org.mabartos.api.service.CapabilityService;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class LightCapability extends GeneralMqttCapability<LightCapModel> {

    public LightCapability(BartMqttClient client, CapabilityService capabilityService, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(client, capabilityService, capabilityTopic, message);
    }
}
