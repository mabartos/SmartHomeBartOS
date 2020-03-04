package org.mabartos.protocols.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.capability.TemperatureCapModel;
import org.mabartos.protocols.mqtt.data.BartMqttSender;
import org.mabartos.protocols.mqtt.data.capability.TemperatureData;
import org.mabartos.protocols.mqtt.exceptions.WrongMessageTopicException;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

public class TemperatureCapability extends GeneralMqttCapability {

    private TemperatureData tempMessage;

    public TemperatureCapability() {
        super();
    }

    public TemperatureCapability(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(services, client, capabilityTopic, message);
        try {
            this.tempMessage = TemperatureData.fromJson(message.toString());
        } catch (WrongMessageTopicException e) {
            BartMqttSender.sendResponse(client, 400, "Wrong message");
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
                    BartMqttSender.sendResponse(client, 200, "Temperature was updated");
                    return;
                }
            }
        }
        BartMqttSender.sendResponse(client, 400, "Update temperature");
    }
}
