package org.mabartos.protocols.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.protocols.mqtt.data.capability.LightsData;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class LightCapability extends GeneralMqttCapability<LightsData> {

    public LightCapability(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(services, client, capabilityTopic, message);
        this.data = LightsData.fromJson(message.toString());
    }
}
