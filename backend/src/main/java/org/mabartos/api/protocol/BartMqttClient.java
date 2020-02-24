package org.mabartos.api.protocol;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.mabartos.persistence.model.HomeModel;

public interface BartMqttClient {

    void init(String brokerUrl, HomeModel home);

    String getBrokerURL();

    void setBrokerURL(String brokerURL);

    String getClientID();

    IMqttClient getMqttClient();

    String getTopic();

    boolean publish(String topic, String message);
}