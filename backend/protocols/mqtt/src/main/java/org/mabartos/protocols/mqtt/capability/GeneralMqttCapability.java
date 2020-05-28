/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.protocols.mqtt.capability;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.data.general.capability.CapabilityData;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.api.protocol.mqtt.BartMqttClient;
import org.mabartos.api.protocol.mqtt.topics.CapabilityTopic;
import org.mabartos.api.service.AppServices;

import java.util.logging.Logger;

public abstract class GeneralMqttCapability<Data extends CapabilityData> {

    protected static Logger logger = Logger.getLogger(GeneralMqttCapability.class.getName());

    protected MqttMessage message;
    protected CapabilityModel model;
    protected CapabilityTopic capabilityTopic;
    protected AppServices services;
    protected BartMqttClient client;

    protected Data data;

    public GeneralMqttCapability(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
        this.services = services;
        this.capabilityTopic = capabilityTopic;
        this.message = message;
        this.model = services.capabilities().findByID(capabilityTopic.getCapabilityID());
        this.client = client;
    }

    public void parseMessage() {
        ParseUtils.parse(services, capabilityTopic, data);
    }
}
