package org.mabartos.services.model.user;

import com.google.common.collect.Sets;
import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.model.user.UserRoleModel;
import org.mabartos.api.service.user.UserRoleService;
import org.mabartos.persistence.jpa.repository.UserRoleRepository;
import org.mabartos.services.model.CRUDServiceImpl;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.Query;
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
    public int deleteAllRolesFromHome(Long homeID) {
        Query query = getEntityManager().createNamedQuery("deleteAllRolesFromHome");
        query.setParameter("homeID", homeID);
        return query.executeUpdate();
    }
}
