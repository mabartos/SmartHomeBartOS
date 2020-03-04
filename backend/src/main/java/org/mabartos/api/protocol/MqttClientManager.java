package org.mabartos.api.protocol;

public interface MqttClientManager {

    void initAllClients();

    void initClient(Long id);

    void shutdownClient(Long id);
}
