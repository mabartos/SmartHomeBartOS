package org.mabartos.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.persistence.model.CapabilityModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CapabilityRepository implements PanacheRepository<CapabilityModel> {
}
