/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.services.model;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.mqtt.MqttClientModel;
import org.mabartos.api.protocol.mqtt.MqttClientService;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.jpa.model.mqtt.MqttClientEntity;
import org.mabartos.persistence.jpa.repository.BartMqttClientRepository;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Dependent
public class MqttClientServiceImpl extends CRUDServiceImpl<MqttClientModel, MqttClientEntity, BartMqttClientRepository, Long> implements MqttClientService {

    @Inject
    AppServices services;

    public void start(@Observes StartupEvent event) {
    }

    @Inject
    MqttClientServiceImpl(BartMqttClientRepository repository) {
        super(repository);
    }

    @Override
    public MqttClientModel getByHome(HomeModel home) {
        return services.homes().findByID(home.getID()).getMqttClient();
    }

    @Override
    public MqttClientModel getByHomeID(Long homeID) {
        return services.homes().findByID(homeID).getMqttClient();

    }
}
