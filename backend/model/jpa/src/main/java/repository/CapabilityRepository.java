package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.capability.CapabilityModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CapabilityRepository implements PanacheRepository<CapabilityModel> {
}
