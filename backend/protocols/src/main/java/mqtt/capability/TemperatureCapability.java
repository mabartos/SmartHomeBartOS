package mqtt.capability;

import models.capability.CapabilityModel;
import models.capability.TemperatureCapModel;
import mqtt.exceptions.WrongMessageTopicException;
import mqtt.messages.BartMqttSender;
import mqtt.messages.capability.TemperatureCapMessage;
import mqtt.topics.CapabilityTopic;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import protocol.BartMqttClient;
import service.CapabilityService;

public class TemperatureCapability extends GeneralMqttCapability<TemperatureCapModel> {

    private TemperatureCapMessage tempMessage;

    public TemperatureCapability() {
        super();
    }

    public TemperatureCapability(BartMqttClient client, CapabilityService capabilityService, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(client, capabilityService, capabilityTopic, message);
        try {
            this.tempMessage = TemperatureCapMessage.fromJson(message.toString());
        } catch (WrongMessageTopicException e) {
            BartMqttSender.sendResponse(client, 400, "Wrong message");
        }
    }

    @Override
    public void parseMessage() {
        if (tempMessage != null) {
            CapabilityModel model = capabilityService.findByID(capabilityTopic.getCapabilityID());
            if (model instanceof TemperatureCapModel) {
                TemperatureCapModel result = (TemperatureCapModel) model;
                result.setValue(tempMessage.getActualTemperature());
                CapabilityModel updated = capabilityService.updateByID(capabilityTopic.getCapabilityID(), model);
                if (updated != null) {
                    BartMqttSender.sendResponse(client, 200, "Temperature was updated");
                    return;
                }
            }
        }
        BartMqttSender.sendResponse(client, 400, "Update temperature");
    }
}
