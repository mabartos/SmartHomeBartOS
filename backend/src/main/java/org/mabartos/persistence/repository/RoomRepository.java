package org.mabartos.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.persistence.model.room.RoomModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoomRepository implements PanacheRepository<RoomModel> {
}
