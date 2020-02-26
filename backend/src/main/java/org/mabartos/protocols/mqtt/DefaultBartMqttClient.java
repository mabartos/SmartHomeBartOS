package org.mabartos.protocols.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.protocols.mqtt.utils.TopicUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class DefaultBartMqttClient implements BartMqttClient, Serializable {
    public static Logger logger = Logger.getLogger(DefaultBartMqttClient.class.getName());

    private final Integer TIMEOUT = 20;
    private final Integer STD_QOS = 2;

    private String brokerURL;
    private String clientID;
    private IMqttClient mqttClient;
    private HomeModel home;
    private BartMqttClient actual;

    @Inject
    BartMqttHandler handler;

    @Inject
    AppServices services;

    public DefaultBartMqttClient() {
        logger.info("Initialized");
        this.services = services;
        this.handler = handler;

    }

    @PostConstruct
    public void start() {
        System.out.println("heeeere");
        // services.homes().getAll().forEach(System.out::println);
        // services.homes().getAll().forEach(this::initClient);
    }

    @Override
    public void initClient(HomeModel home) {
        try {
            this.clientID = UUID.randomUUID().toString();
            this.home = home;
            this.mqttClient = new MqttClient(home.getMqttClient().getBrokerURL(), this.getClientID());
            this.brokerURL = home.getMqttClient().getBrokerURL();
            this.actual = this;

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    logger.warning("Connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    handler.executeMessage(actual, home, topic, message);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });
            mqttClient.connect(setConnectOptions());
            mqttClient.subscribe(TopicUtils.getHomeTopic(home) + "/#", STD_QOS);
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

    public boolean reconnectClient() {
        try {
            if (mqttClient != null) {
                mqttClient.reconnect();
                return true;
            }
        } catch (MqttException ignored) {
        }
        return false;
    }

    @Override
    public String getBrokerURL() {
        return brokerURL;
    }

    @Override
    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    @Override
    public String getClientID() {
        return clientID;
    }

    @Override
    public IMqttClient getMqttClient() {
        return mqttClient;
    }

    @Override
    public String getTopic() {
        if (home != null) {
            return home.getMqttClient().getTopic();
        }
        return null;
    }

    @Override
    public HomeModel getHome() {
        return home;
    }

    @Override
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