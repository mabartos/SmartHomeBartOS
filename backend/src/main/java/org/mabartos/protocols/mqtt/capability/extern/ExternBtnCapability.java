package org.mabartos.protocols.mqtt.capability.extern;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.protocols.mqtt.capability.GeneralMqttCapability;
import org.mabartos.protocols.mqtt.data.capability.extern.ExternBtnData;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class ExternBtnCapability extends GeneralMqttCapability<ExternBtnData> {

    public ExternBtnCapability(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(services, client, capabilityTopic, message);
        this.data = ExternBtnData.fromJson(message.toString());
    }
}
