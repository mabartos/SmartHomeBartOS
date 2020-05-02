package org.mabartos.persistence.jpa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.services.model.user.UserEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {
}
