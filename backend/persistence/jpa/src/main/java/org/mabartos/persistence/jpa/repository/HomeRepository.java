package org.mabartos.persistence.jpa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.services.model.home.HomeEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HomeRepository implements PanacheRepository<HomeEntity> {
}
