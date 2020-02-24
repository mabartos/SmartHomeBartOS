package org.mabartos.protocols.mqtt.messages;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.mabartos.api.protocol.BartMqttClient;

public class BarMqttSender {

    public static boolean sendResponse(BartMqttClient client, String topic, HttpResponseStatus status, String message) {
        HttpResponseJSON response = new HttpResponseJSON(status, message);
        return client.publish(topic, response.toJson());
    }

    public static boolean sendResponse(BartMqttClient client, String topic, Integer statusCode, String message) {
        return sendResponse(client, topic, HttpResponseStatus.valueOf(statusCode), message);
    }

    public static boolean sendResponse(BartMqttClient client, Integer statusCode, String message) {
        return sendResponse(client, client.getTopic(), statusCode, message);
    }
}
