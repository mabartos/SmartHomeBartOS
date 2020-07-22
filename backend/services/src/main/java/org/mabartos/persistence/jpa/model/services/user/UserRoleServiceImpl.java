/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.user;

import com.google.common.collect.Sets;
import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.controller.user.UserRoleData;
import org.mabartos.api.model.user.UserRoleModel;
import org.mabartos.api.service.user.UserRoleService;
import org.mabartos.persistence.jpa.repository.UserRoleRepository;
import org.mabartos.services.model.CRUDServiceImpl;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.Query;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Dependent
public class UserRoleServiceImpl extends CRUDServiceImpl<UserRoleModel, UserRoleEntity, UserRoleRepository, Long> implements UserRoleService {

    public void start(@Observes StartupEvent event) {
    }

    @Inject
    public UserRoleServiceImpl(UserRoleRepository repository) {
        super(repository);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<UserRoleModel> getAllUserRoles(UUID userID) {
        Query query = getEntityManager().createNamedQuery("getAllUserRoleByUUID");
        query.setParameter("userID", userID);
        return Sets.newHashSet(query.getResultList());
    }

    @Override
    public Set<UserRoleData> getAllUserRolesData(UUID userID) {
        Set<UserRoleModel> roleModels = getAllUserRoles(userID);
        Set<UserRoleData> roleData = new HashSet<>();
        if (roleModels != null) {
            roleModels.forEach(role -> roleData.add(new UserRoleData(role.getHomeID(), role.getRole())));
            return roleData;
        }
        return Collections.emptySet();
    }

    @Override
    public int deleteAllRolesFromHome(Long homeID) {
        getEntityManager().flush();
        getEntityManager().clear();

        Query query = getEntityManager().createNamedQuery("deleteAllRolesFromHome");
        query.setParameter("homeID", homeID);
        return query.executeUpdate();
    }
}
