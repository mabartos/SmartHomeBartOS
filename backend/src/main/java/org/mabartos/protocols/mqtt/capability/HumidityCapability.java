package org.mabartos.protocols.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.model.BartSession;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class HumidityCapability extends GeneralMqttCapability {

    public HumidityCapability(BartSession session, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(session, capabilityTopic, message);
    }

}
