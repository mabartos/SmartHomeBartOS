package org.mabartos.streams.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.UUID;

public class BarMqttClient {

    private final Integer TIMEOUT = 10;

    private String brokerURL;
    private String clientID;
    private IMqttClient mqttClient;

    public BarMqttClient(String brokerURL, String topic) {
        try {
            this.brokerURL = brokerURL;
            this.clientID = UUID.randomUUID().toString();
            this.mqttClient = new MqttClient(this.getBrokerURL(), this.getClientID());

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println(topic + ": " + message.toString());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            mqttClient.connect(setConnectOptions());
            mqttClient.subscribe(topic + "/#");

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private MqttConnectOptions setConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(TIMEOUT);
        return options;
    }

    public String getBrokerURL() {
        return brokerURL;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    public String getClientID() {
        return clientID;
    }

    public IMqttClient getMqttClient() {
        return mqttClient;
    }

    public void publish(String topic, String message) {
        try {
            mqttClient.publish(topic, new MqttMessage(message.getBytes()));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
