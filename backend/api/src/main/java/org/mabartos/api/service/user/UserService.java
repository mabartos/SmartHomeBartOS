package org.mabartos.api.service.user;

import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.service.CRUDService;

import java.util.UUID;

public interface UserService extends CRUDService<UserModel, UUID> {

    UserRoleService roles();

    UserModel findByUsername(String username);

    UserModel findByEmail(String email);
}
