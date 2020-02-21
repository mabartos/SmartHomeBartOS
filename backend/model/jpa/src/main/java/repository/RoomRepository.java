package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.RoomModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoomRepository implements PanacheRepository<RoomModel> {
}
