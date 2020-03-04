package org.mabartos.protocols.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.protocols.mqtt.data.BartMqttSender;
import org.mabartos.protocols.mqtt.data.capability.TemperatureData;
import org.mabartos.protocols.mqtt.exceptions.WrongMessageTopicException;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class TemperatureCapability extends GeneralMqttCapability {

    private TemperatureData tempData;

    public TemperatureCapability() {
        super();
    }

    public TemperatureCapability(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(services, client, capabilityTopic, message);
        this.tempData = getTempDataFromJson();
    }

    private TemperatureData getTempDataFromJson() {
        try {
            return TemperatureData.fromJson(message.toString());
        } catch (WrongMessageTopicException e) {
            BartMqttSender.sendResponse(client, 400, "Wrong message");
        }
        return null;
    }

    @Override
    public void parseMessage() {
        ParseUtils.parse(services, client, capabilityTopic, tempData);
    }
}
