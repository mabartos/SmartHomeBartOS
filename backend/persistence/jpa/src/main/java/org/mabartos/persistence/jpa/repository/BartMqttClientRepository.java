package org.mabartos.persistence.jpa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.persistence.jpa.model.mqtt.MqttClientEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BartMqttClientRepository implements PanacheRepository<MqttClientEntity> {
}
