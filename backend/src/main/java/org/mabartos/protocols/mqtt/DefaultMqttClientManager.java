package org.mabartos.protocols.mqtt;

import io.quarkus.runtime.ShutdownEvent;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.protocol.MqttClientManager;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.HomeModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@ApplicationScoped
public class DefaultMqttClientManager implements MqttClientManager {

    public static Logger logger = Logger.getLogger(DefaultMqttClientManager.class.getName());

    private static Set<BartMqttClient> clients = new HashSet<>();
    private static MemoryPersistence persistence = new MemoryPersistence();

    public void onDestroy(@Observes ShutdownEvent end) {
        destroyAllClients();
    }

    AppServices services;
    BartMqttHandler handler;

    @Inject
    public DefaultMqttClientManager(AppServices services, BartMqttHandler handler) {
        this.services = services;
        this.handler = handler;
    }

    @Override
    public boolean initAllClients() {
        try {
            services.homes().getAll().forEach(home -> {
                clients.add(new DefaultBartMqttClient(home, handler, persistence));
            });
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean destroyAllClients() {
        try {
            clients.forEach(client -> {
                try {
                    client.getMqttClient().disconnect();
                    client.getMqttClient().close();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            });
            clients = Collections.emptySet();
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean initClient(Long idHome) {
        try {
            HomeModel home = services.homes().findByID(idHome);
            if (home != null) {
                clients.add(new DefaultBartMqttClient(home, handler, persistence));
                return true;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean shutdownClient(Long id) {
        try {
            clients.stream()
                    .filter(f -> f.getHome().getID().equals(id))
                    .findFirst()
                    .ifPresent(client -> {
                        try {
                            client.getMqttClient().disconnect();
                            client.getMqttClient().close();
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    });
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }
}
