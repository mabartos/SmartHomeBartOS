package org.mabartos.protocols.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.protocols.mqtt.data.BartMqttSender;
import org.mabartos.protocols.mqtt.data.capability.LightsData;
import org.mabartos.protocols.mqtt.exceptions.WrongMessageTopicException;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class LightCapability extends GeneralMqttCapability {

    private LightsData lightsData;

    public LightCapability(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(services, client, capabilityTopic, message);
        this.lightsData = getLightsDataFromJson();
    }

    private LightsData getLightsDataFromJson() {
        try {
            return LightsData.fromJson(message.toString());
        } catch (WrongMessageTopicException e) {
            BartMqttSender.sendResponse(client, 400, "Wrong message");
        }
        return null;
    }

    @Override
    public void parseMessage() {
        ParseUtils.parse(services, client, capabilityTopic, lightsData);
    }
}
