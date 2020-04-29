package org.mabartos.protocols.mqtt.capability.relay;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.protocols.mqtt.capability.GeneralMqttCapability;
import org.mabartos.protocols.mqtt.data.capability.relay.RelayData;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class RelayCapability extends GeneralMqttCapability<RelayData> {

    public RelayCapability(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(services, client, capabilityTopic, message);
        this.data = RelayData.fromJson(message.toString());
    }
}
