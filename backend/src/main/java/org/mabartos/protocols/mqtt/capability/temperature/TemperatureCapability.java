package org.mabartos.protocols.mqtt.capability.temperature;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.protocols.mqtt.capability.GeneralMqttCapability;
import org.mabartos.protocols.mqtt.data.capability.temperature.TemperatureData;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class TemperatureCapability extends GeneralMqttCapability<TemperatureData> {

    public TemperatureCapability(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(services, client, capabilityTopic, message);
        this.data = TemperatureData.fromJson(message.toString());
    }
}
