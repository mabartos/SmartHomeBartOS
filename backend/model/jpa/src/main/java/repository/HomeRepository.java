package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.HomeModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HomeRepository implements PanacheRepository<HomeModel> {
}
