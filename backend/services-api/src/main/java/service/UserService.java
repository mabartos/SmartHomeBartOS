package service;

import models.UserModel;

public interface UserService extends CRUDService<UserModel> {

    UserModel findByUsername(String username);

    UserModel findByEmail(String email);
}
