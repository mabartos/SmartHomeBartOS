package org.mabartos.service.impl;

import org.mabartos.persistence.model.UserModel;
import org.mabartos.persistence.repository.UserRepository;
import org.mabartos.service.core.UserService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class UserServiceImpl extends CRUDServiceImpl<UserModel, UserRepository> implements UserService {

    @Inject
    UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public UserModel findByUsername(String username) {
        return getRepository().find("username", username).firstResult();
    }

    @Override
    public UserModel findByEmail(String email) {
        return getRepository().find("email", email).firstResult();
    }
}
