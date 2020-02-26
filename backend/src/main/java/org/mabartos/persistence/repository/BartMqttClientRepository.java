package org.mabartos.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.persistence.model.MqttClientModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BartMqttClientRepository implements PanacheRepository<MqttClientModel> {
}
