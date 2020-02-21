package impl;

import models.UserModel;
import repository.UserRepository;
import service.UserService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class UserServiceImpl extends CRUDServiceImpl<UserModel, UserRepository> implements UserService {

    @Inject
    UserServiceImpl() {
        super(UserRepository.class);
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
