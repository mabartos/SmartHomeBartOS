package org.mabartos.services.model.user;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.service.user.UserRoleService;
import org.mabartos.api.service.user.UserService;
import org.mabartos.persistence.model.user.UserModel;
import org.mabartos.persistence.repository.UserRepository;
import org.mabartos.services.model.CRUDServiceImpl;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.UUID;

@Dependent
public class UserServiceImpl extends CRUDServiceImpl<UserModel, UserRepository, UUID> implements UserService {

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
            return (UserModel) getEntityManager().createQuery("select user from UserModel user where user.uuid = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
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
}