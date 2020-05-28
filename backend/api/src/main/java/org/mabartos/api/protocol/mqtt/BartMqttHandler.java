/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.protocol.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.model.home.HomeModel;

import java.io.Serializable;

public interface BartMqttHandler extends Serializable {

    void executeMessage(BartMqttClient client, HomeModel home, final String rawTopic, final MqttMessage message);
}
