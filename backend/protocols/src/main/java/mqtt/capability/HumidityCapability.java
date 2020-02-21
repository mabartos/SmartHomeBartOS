package mqtt.capability;

import models.capability.HumidityCapModel;
import mqtt.topics.CapabilityTopic;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import protocol.BartMqttClient;
import service.CapabilityService;

public class HumidityCapability extends GeneralMqttCapability<HumidityCapModel> {

    public HumidityCapability(BartMqttClient client, CapabilityService capabilityService, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(client, capabilityService, capabilityTopic, message);
    }

}
