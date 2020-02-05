package org.mabartos.streams.mqtt.messages;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.mabartos.streams.mqtt.BarMqttClient;

public class BarMqttSender {

    public static boolean sendAddDeviceResponse(BarMqttClient client, String topic, HttpResponseStatus status, String message) {
        MqttCRUDResponse response = new MqttCRUDResponse(status, message);
        return client.publish(topic, response.toJson());
    }

    public static boolean sendAddDeviceResponse(BarMqttClient client, String topic, Integer statusCode, String message) {
        return sendAddDeviceResponse(client, topic, HttpResponseStatus.valueOf(statusCode), message);
    }
}
