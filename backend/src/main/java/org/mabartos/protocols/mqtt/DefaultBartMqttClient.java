package org.mabartos.protocols.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.protocols.mqtt.utils.TopicUtils;

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Logger;

public class DefaultBartMqttClient implements BartMqttClient, Serializable {

    public static Logger logger = Logger.getLogger(DefaultBartMqttClient.class.getName());

    private final Integer TIMEOUT = 20;
    private final Integer STD_QOS = 2;

    private MemoryPersistence persistence;
    private String brokerURL;
    private String clientID;
    private IMqttClient mqttClient;
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
            this.mqttClient = new MqttClient(home.getMqttClient().getBrokerURL(), this.getClientID(), persistence);

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
                    services.getVertx().eventBus().publish("test", "COMPLETED " + home.getName());
                }
            });
            mqttClient.connect(setConnectOptions());
            mqttClient.subscribe(TopicUtils.getHomeTopic(home) + "/#", STD_QOS);

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
