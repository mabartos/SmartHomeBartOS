package org.mabartos.streams.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.streams.mqtt.devices.BarMqttHandler;
import org.mabartos.streams.mqtt.utils.TopicUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class BarMqttClient {

    private final Integer TIMEOUT = 10;

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

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    new BarMqttHandler().executeMessage(home, thisClient, topic, message);
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

    public void publish(String topic, String message) {
        try {
            mqttClient.publish(topic, new MqttMessage(message.getBytes()));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
