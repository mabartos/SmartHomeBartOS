package org.mabartos.services.model.user;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.service.user.UserRoleService;
import org.mabartos.persistence.model.user.UserRoleModel;
import org.mabartos.persistence.repository.UserRoleRepository;
import org.mabartos.services.model.CRUDServiceImpl;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.Query;

@Dependent
public class UserRoleServiceImpl extends CRUDServiceImpl<UserRoleModel, UserRoleRepository, Long> implements UserRoleService {

    public void start(@Observes StartupEvent event) {
    }

    @Inject
    public UserRoleServiceImpl(UserRoleRepository repository) {
        super(repository);
    }

    @Override
    public int deleteAllRolesFromHome(Long homeID) {
        Query query = getEntityManager().createNamedQuery("deleteAllRolesFromHome");
        query.setParameter("homeID", homeID);
        return query.executeUpdate();
    }
}
