package org.mabartos.streams.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.streams.mqtt.utils.TopicUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class BarMqttClient {

    @Inject
    BarMqttHandler handler;

    private final Integer TIMEOUT = 20;

    private String brokerURL;
    private String clientID;
    private IMqttClient mqttClient;
    private BarMqttClient thisClient;
    private HomeModel home;

    @Inject
    public BarMqttClient() {
    }

    public BarMqttClient(String brokerURL, HomeModel home) {
        init(brokerURL, home);
    }

    public void init(String brokerURL, HomeModel home) {
        try {
            this.brokerURL = brokerURL;
            this.clientID = UUID.randomUUID().toString();
            this.mqttClient = new MqttClient(this.getBrokerURL(), this.getClientID());
            this.thisClient = this;
            this.home = home;

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost");
                    System.out.println(cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    handler.executeMessage(home, thisClient, topic, message);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            mqttClient.connect(setConnectOptions());
            mqttClient.subscribe(TopicUtils.getTopic(home) + "/#");

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

    public String getTopic() {
        if (home != null) {
            return home.getTopic();
        }
        return null;
    }

    public boolean publish(String topic, String message) {
        try {
            mqttClient.publish(topic, new MqttMessage(message.getBytes()));
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }
}
