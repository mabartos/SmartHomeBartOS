package mqtt.capability;

import models.capability.LightCapModel;
import mqtt.topics.CapabilityTopic;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import protocol.BartMqttClient;
import service.CapabilityService;

public class LightCapability extends GeneralMqttCapability<LightCapModel> {

    public LightCapability(BartMqttClient client, CapabilityService capabilityService, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(client, capabilityService, capabilityTopic, message);
    }
}
