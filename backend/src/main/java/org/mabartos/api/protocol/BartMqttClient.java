package org.mabartos.api.protocol;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.protocols.mqtt.BartMqttHandler;

public interface BartMqttClient {

    void init(HomeModel home, BartMqttHandler handler, MemoryPersistence persistence);

    boolean reconnectClient();

    String getBrokerURL();

    void setBrokerURL(String brokerURL);

    String getClientID();

    IMqttClient getMqttClient();

    String getTopic();

    HomeModel getHome();

    boolean publish(String topic, String message);
}