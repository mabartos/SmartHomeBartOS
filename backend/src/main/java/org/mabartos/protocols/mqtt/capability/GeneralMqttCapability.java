package org.mabartos.protocols.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.model.BartSession;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.protocols.mqtt.topics.CapabilityTopic;

import java.util.logging.Logger;

public class GeneralMqttCapability {

    protected static Logger logger = Logger.getLogger(GeneralMqttCapability.class.getName());

    protected MqttMessage message;
    protected CapabilityModel model;
    protected CapabilityTopic capabilityTopic;
    protected BartSession session;

    public GeneralMqttCapability() {
    }

    public GeneralMqttCapability(BartSession session, CapabilityTopic capabilityTopic, MqttMessage message) {
        this.session = session;
        this.message = message;
        this.capabilityTopic = capabilityTopic;
        this.model = session.capabilities().findByID(capabilityTopic.getCapabilityID());
    }

    public void parseMessage() {
        logger.info("Device type: " + capabilityTopic.getCapabilityType() + ", message: " + message);
    }
}
