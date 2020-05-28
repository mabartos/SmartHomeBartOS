/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.protocols.mqtt.capability.light;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.data.general.capability.light.LightsData;
import org.mabartos.api.protocol.mqtt.BartMqttClient;
import org.mabartos.api.protocol.mqtt.topics.CapabilityTopic;
import org.mabartos.api.service.AppServices;
import org.mabartos.protocols.mqtt.capability.GeneralMqttCapability;

public class LightCapability extends GeneralMqttCapability<LightsData> {

    public LightCapability(AppServices services, BartMqttClient client, CapabilityTopic capabilityTopic, MqttMessage message) {
        super(services, client, capabilityTopic, message);
        this.data = LightsData.fromJson(message.toString());
    }
}
