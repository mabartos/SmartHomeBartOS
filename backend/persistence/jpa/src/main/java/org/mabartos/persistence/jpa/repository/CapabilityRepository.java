package org.mabartos.persistence.jpa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.services.model.capability.CapabilityEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CapabilityRepository implements PanacheRepository<CapabilityEntity> {
}
