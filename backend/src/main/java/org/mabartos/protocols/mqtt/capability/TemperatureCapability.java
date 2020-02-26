package org.mabartos.protocols.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.TemperatureCapModel;
import org.mabartos.protocols.mqtt.exceptions.WrongMessageTopicException;
import org.mabartos.protocols.mqtt.messages.BarMqttSender;
import org.mabartos.protocols.mqtt.messages.capability.TemperatureCapMessage;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class TemperatureCapability extends GeneralMqttCapability {

    private TemperatureCapMessage tempMessage;

    public TemperatureCapability() {
        super();
    }

    public TemperatureCapability(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(services, client, capabilityTopic, message);
        try {
            this.tempMessage = TemperatureCapMessage.fromJson(message.toString());
        } catch (WrongMessageTopicException e) {
            BarMqttSender.sendResponse(client, 400, "Wrong message");
        }
    }

    @Override
    public void parseMessage() {
        if (tempMessage != null) {
            CapabilityModel model = services.capabilities().findByID(capabilityTopic.getCapabilityID());
            if (model instanceof TemperatureCapModel) {
                TemperatureCapModel result = (TemperatureCapModel) model;
                result.setValue(tempMessage.getActualTemperature());
                CapabilityModel updated = services.capabilities().updateByID(capabilityTopic.getCapabilityID(), model);
                if (updated != null) {
                    BarMqttSender.sendResponse(client, 200, "Temperature was updated");
                    return;
                }
            }
        }
        BarMqttSender.sendResponse(client, 400, "Update temperature");
    }
}
