/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.mqtt;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.mabartos.api.protocol.mqtt.BartMqttClient;

public class BartMqttSender {

    public static boolean sendResponse(BartMqttClient client, String topic, HttpResponseStatus status, String message) {
        HttpResponseData response = new HttpResponseData(status, message);
        return client.publish(topic, response.toJson());
    }

    public static boolean sendResponse(BartMqttClient client, String topic, HttpResponseStatus status) {
        HttpResponseData response = new HttpResponseData(status, "");
        return client.publish(topic, response.toJson());
    }

    public static boolean sendResponse(BartMqttClient client, String topic, Integer statusCode, String message) {
        return sendResponse(client, topic, HttpResponseStatus.valueOf(statusCode), message);
    }

    public static boolean sendResponse(BartMqttClient client, Integer statusCode, String message) {
        return sendResponse(client, client.getTopic(), statusCode, message);
    }
}
