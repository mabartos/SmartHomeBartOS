/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.services.protocols.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.protocol.mqtt.BartMqttClient;
import org.mabartos.api.protocol.mqtt.BartMqttHandler;
import org.mabartos.api.protocol.mqtt.TopicUtils;
import org.mabartos.api.service.AppServices;

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Logger;

public class DefaultBartMqttClient implements BartMqttClient, Serializable {

    public static Logger logger = Logger.getLogger(DefaultBartMqttClient.class.getName());

    private final Integer TIMEOUT = 20;
    private final Integer SUBSCRIBE_QOS = 2;

    private MemoryPersistence persistence;
    private String brokerURL;
    private String clientID;
    private IMqttAsyncClient mqttClient;
    private HomeModel home;
    private AppServices services;

    private boolean previousState = false;

    public DefaultBartMqttClient(AppServices services, HomeModel home, BartMqttHandler handler) {
        init(services, home, handler);
    }

    public void init(AppServices services, HomeModel home, BartMqttHandler handler) {
        this.services = services;
        this.persistence = new MemoryPersistence();
        this.home = home;
        this.previousState = home.getMqttClient().isBrokerActive();
        initOnlyMqttClient(handler);
    }

    private void initOnlyMqttClient(BartMqttHandler handler) {
        try {
            if (home.getMqttClient() != null && home.getMqttClient().isBrokerActive()) {
                return;
            }

            this.clientID = UUID.randomUUID().toString();
            this.brokerURL = home.getMqttClient().getBrokerURL();
            BartMqttClient actual = this;
            this.mqttClient = new MqttAsyncClient(home.getMqttClient().getBrokerURL(), this.getClientID(), persistence);

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    setState(false);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    handler.executeMessage(actual, home, topic, message);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    setBrokerActive(true);
                }
            });
            IMqttToken token = mqttClient.connect(setConnectOptions());
            token.waitForCompletion();

            mqttClient.subscribe(TopicUtils.getHomeTopic(home) + "/#", SUBSCRIBE_QOS);

            checkAndSetState(true);
            logger.info("Initialized MQTT for home " + home.getName());
        } catch (MqttException e) {
            checkAndSetState(false);
        } catch (Exception e) {
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

    private void setBrokerActive(boolean state) {
        previousState = state;
        if (this.home.getMqttClient().isBrokerActive() != state)
            this.home.getMqttClient().setBrokerActive(state);
    }

    private void checkAndSetState(boolean invokeState) {
        if (previousState != invokeState) {
            setState(invokeState);
        }
    }

    private void setState(boolean state) {
        setBrokerActive(state);
        services.homes().updateByID(home.getID(), home);
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
    public IMqttAsyncClient getMqttClient() {
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

    @Override
    public boolean publish(String topic, MqttMessage message) {
        try {
            mqttClient.publish(topic, message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean publish(String topic, String message, int qos) {
        return publish(topic, message, qos, true);
    }

    @Override
    public boolean publish(String topic, String message, int qos, boolean retained) {
        try {
            mqttClient.publish(topic, message.getBytes(), qos, retained);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }
}
