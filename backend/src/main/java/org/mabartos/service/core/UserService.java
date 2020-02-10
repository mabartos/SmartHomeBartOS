package org.mabartos.service.core;

import org.mabartos.persistence.model.UserModel;

public interface UserService extends CRUDService<UserModel> {

    UserModel findByUsername(String username);

    UserModel findByEmail(String email);
}
