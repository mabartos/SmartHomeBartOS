package org.mabartos.protocols.mqtt.capability.pir;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.protocols.mqtt.capability.GeneralMqttCapability;
import org.mabartos.protocols.mqtt.data.capability.pir.PIRData;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class PIRCapability extends GeneralMqttCapability<PIRData> {
    public PIRCapability(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(services, client, capabilityTopic, message);
        this.data = PIRData.fromJson(message.toString());
    }
}
