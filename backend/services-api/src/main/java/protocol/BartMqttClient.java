package protocol;

import models.HomeModel;
import org.eclipse.paho.client.mqttv3.IMqttClient;

public interface BartMqttClient {

    void init(String brokerUrl, HomeModel home);

    String getBrokerURL();

    void setBrokerURL(String brokerURL);

    String getClientID();

    IMqttClient getMqttClient();

    String getTopic();

    boolean publish(String topic, String message);
}
