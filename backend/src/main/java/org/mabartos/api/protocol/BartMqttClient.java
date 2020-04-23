package org.mabartos.api.protocol;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.home.HomeModel;
import org.mabartos.protocols.mqtt.BartMqttHandler;

public interface BartMqttClient {

    void init(AppServices services, HomeModel home, BartMqttHandler handler);

    boolean reconnectClient();

    String getBrokerURL();

    void setBrokerURL(String brokerURL);

    String getClientID();

    IMqttAsyncClient getMqttClient();

    String getTopic();

    HomeModel getHome();

    boolean publish(String topic, String message);

    boolean publish(String topic, String message, int qos, boolean retained);

    boolean publish(String topic, String message, int qos);

    boolean publish(String topic, MqttMessage message);
}