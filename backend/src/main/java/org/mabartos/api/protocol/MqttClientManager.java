package org.mabartos.api.protocol;

public interface MqttClientManager {

    boolean initAllClients();

    boolean destroyAllClients();

    boolean initClient(Long idHome);

    boolean shutdownClient(Long idHome);

    BartMqttClient getMqttForHome(Long idHome);
}
