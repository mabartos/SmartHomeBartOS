package org.mabartos.streams.mqtt.messages;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.mabartos.streams.mqtt.BarMqttClient;

public class BarMqttSender {

    public static boolean sendResponse(BarMqttClient client, String topic, HttpResponseStatus status, String message) {
        HttpResponseJSON response = new HttpResponseJSON(status, message);
        return client.publish(topic, response.toJson());
    }

    public static boolean sendResponse(BarMqttClient client, String topic, Integer statusCode, String message) {
        return sendResponse(client, topic, HttpResponseStatus.valueOf(statusCode), message);
    }

    public static boolean sendResponse(BarMqttClient client, Integer statusCode, String message) {
        return sendResponse(client, client.getTopic(), statusCode, message);
    }
}
