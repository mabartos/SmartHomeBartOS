/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.services.protocols.mqtt;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.protocol.mqtt.BartMqttClient;
import org.mabartos.api.protocol.mqtt.BartMqttHandler;
import org.mabartos.api.protocol.mqtt.MqttClientManager;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.jpa.repository.ValidityUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

@ApplicationScoped
public class DefaultMqttClientManager implements MqttClientManager {

    public static Logger logger = Logger.getLogger(DefaultMqttClientManager.class.getName());

    private static AtomicReference<Set<BartMqttClient>> clients = new AtomicReference<>(new HashSet<>());

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

    @Scheduled(every = "3s")
    public void verifyAvailability() {
        services.homes()
                .getAll()
                .stream()
                .filter(home -> !home.getMqttClient().isBrokerActive())
                .filter(ValidityUtils::isBrokerURLValid)
                .map(HomeModel::getID)
                .forEach(this::initClient);
    }

    @Override
    public boolean initAllClients() {
        try {
            services.homes()
                    .getAll()
                    .stream()
                    .filter(ValidityUtils::isBrokerURLValid)
                    .forEach(home -> {
                        BartMqttClient initCl = clients.get()
                                .stream()
                                .filter(client -> client.getHome().equals(home))
                                .findFirst()
                                .orElse(null);

                        if (initCl != null) {
                            initCl.init(services, home, handler);
                        } else {
                            initCl = new DefaultBartMqttClient(services, home, handler);
                            clients.get().add(new DefaultBartMqttClient(services, home, handler));
                        }
                        services.homes().updateByID(home.getID(), initCl.getHome());
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
            clients.get().forEach(client -> {
                try {
                    IMqttAsyncClient manageClient = client.getMqttClient();
                    if (manageClient.isConnected()) {
                        manageClient.disconnect();
                        manageClient.close();
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            });
            clients.set(Collections.emptySet());
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
            if (ValidityUtils.isBrokerURLValid(home)) {
                BartMqttClient initCl = getMqttForHome(idHome);

                if (initCl != null) {
                    initCl.init(services, home, handler);
                } else {
                    clients.get().add(new DefaultBartMqttClient(services, home, handler));
                }
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
            clients.get().stream()
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

    @Override
    public BartMqttClient getMqttForHome(Long idHome) {
        return clients.get()
                .stream()
                .filter(f -> f.getHome().getID().equals(idHome))
                .findFirst()
                .orElse(null);
    }
}
