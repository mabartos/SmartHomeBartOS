package mqtt;

import models.HomeModel;
import mqtt.utils.TopicUtils;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import protocol.BartMqttClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class DefaultBartMqttClient implements BartMqttClient {

    @Inject
    BartMqttHandler handler;

    private final Integer TIMEOUT = 20;
    private final Integer STD_QOS = 2;

    private String brokerURL;
    private String clientID;
    private IMqttClient mqttClient;
    private BartMqttClient thisClient;
    private HomeModel home;

    @Inject
    public DefaultBartMqttClient() {
    }

    public DefaultBartMqttClient(String brokerURL, HomeModel home) {
        init(brokerURL, home);
    }

    @Override
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
                    System.out.println("Connection losts");
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
            return home.getTopic();
        }
        return null;
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
