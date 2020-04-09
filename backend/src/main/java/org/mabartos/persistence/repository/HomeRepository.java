package org.mabartos.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.persistence.model.home.HomeModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HomeRepository implements PanacheRepository<HomeModel> {
}
