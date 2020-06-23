/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.user;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.service.user.UserRoleService;
import org.mabartos.api.service.user.UserService;
import org.mabartos.persistence.jpa.repository.UserRepository;
import org.mabartos.services.model.CRUDServiceImpl;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Dependent
public class UserServiceImpl extends CRUDServiceImpl<UserModel, UserEntity, UserRepository, UUID> implements UserService {

    private UserRoleService userRoleService;

    public void start(@Observes StartupEvent event) {
    }

    @Inject
    UserServiceImpl(UserRepository repository, UserRoleService userRoleService) {
        super(repository);
        this.userRoleService = userRoleService;
    }

    @Override
    public UserModel findByID(UUID id) {
        try {
            Query query = getEntityManager().createNamedQuery("findUserByUUID", UserModel.class);
            query.setParameter("id", id);
            return (UserModel) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean deleteByID(UUID id) {
        return getRepository().delete("uuid", id) > 0;
    }

    @Override
    public UserRoleService roles() {
        return userRoleService;
    }

    @Override
    public UserModel findByUsername(String username) {
        return getRepository().find("username", username).firstResultOptional().orElse(null);
    }

    @Override
    public UserModel findByEmail(String email) {
        return getRepository().find("email", email).firstResultOptional().orElse(null);
    }

    @Override
    public List<UserModel> findAllByNameOrEmail(String nameOrEmail) {
        Query query = getEntityManager().createNamedQuery("findUserByNameOrEmail", UserModel.class);
        query.setParameter("name", nameOrEmail);
        query.setMaxResults(20);
        return query.getResultList();
    }
}
