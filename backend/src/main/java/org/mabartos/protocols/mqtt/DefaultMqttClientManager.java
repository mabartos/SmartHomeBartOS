package org.mabartos.protocols.mqtt;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.protocol.MqttClientManager;
import org.mabartos.api.service.AppServices;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.logging.Logger;

@ApplicationScoped
public class DefaultMqttClientManager implements MqttClientManager {

    public static Logger logger = Logger.getLogger(DefaultMqttClientManager.class.getName());

    private HashSet<BartMqttClient> clients = new HashSet<>();

    @Inject
    AppServices services;

    @Inject
    BartMqttHandler handler;

    @Override
    public void initAllClients() {
        MemoryPersistence persistence = new MemoryPersistence();
        services.homes().getAll().forEach(home -> {
            clients.add(new DefaultBartMqttClient(home, handler, persistence));
        });
    }

    @Override
    public void initClient(Long id) {

    }

    @Override
    public void shutdownClient(Long id) {

    }
}
