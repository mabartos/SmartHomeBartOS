package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.DeviceModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeviceRepository implements PanacheRepository<DeviceModel> {
}
