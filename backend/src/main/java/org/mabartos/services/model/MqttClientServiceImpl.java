package org.mabartos.services.model;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.model.MqttClientService;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.MqttClientModel;
import org.mabartos.persistence.repository.BartMqttClientRepository;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Dependent
public class MqttClientServiceImpl extends CRUDServiceImpl<MqttClientModel, BartMqttClientRepository, Long> implements MqttClientService {

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
