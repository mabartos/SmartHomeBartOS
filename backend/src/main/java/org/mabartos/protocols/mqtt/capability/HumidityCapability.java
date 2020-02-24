package org.mabartos.protocols.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.CapabilityService;
import org.mabartos.persistence.model.capability.HumidityCapModel;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class HumidityCapability extends GeneralMqttCapability<HumidityCapModel> {

    public HumidityCapability(BartMqttClient client, CapabilityService capabilityService, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(client, capabilityService, capabilityTopic, message);
    }

}
