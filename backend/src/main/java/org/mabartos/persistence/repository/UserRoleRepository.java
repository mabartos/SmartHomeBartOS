package org.mabartos.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mabartos.persistence.model.user.UserRoleModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRoleRepository implements PanacheRepository<UserRoleModel> {
}
