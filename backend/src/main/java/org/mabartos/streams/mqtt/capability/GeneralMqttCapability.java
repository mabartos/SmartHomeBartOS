package org.mabartos.streams.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.service.core.CapabilityService;
import org.mabartos.streams.mqtt.BarMqttClient;
import org.mabartos.streams.mqtt.topics.CapabilityTopic;

import java.util.logging.Logger;

public class GeneralMqttCapability<Model> {

    protected static Logger logger = Logger.getLogger(GeneralMqttCapability.class.getName());

    protected CapabilityService capabilityService;
    protected MqttMessage message;
    protected CapabilityModel model;
    protected CapabilityTopic capabilityTopic;
    protected BarMqttClient client;

    public GeneralMqttCapability() {
    }

    public GeneralMqttCapability(BarMqttClient client, CapabilityService capabilityService, CapabilityTopic capabilityTopic, MqttMessage message) {
        this.capabilityService = capabilityService;
        this.message = message;
        this.capabilityTopic = capabilityTopic;
        this.client = client;
        if (capabilityService != null) {
            this.model = capabilityService.findByID(capabilityTopic.getCapabilityID());
        }
    }

    public void parseMessage() {
        logger.info("Device type: " + capabilityTopic.getCapabilityType() + ", message: " + message);
    }
}
