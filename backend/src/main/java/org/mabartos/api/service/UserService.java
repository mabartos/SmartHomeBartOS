package org.mabartos.api.service;

import org.mabartos.persistence.model.UserModel;

import java.util.UUID;

public interface UserService extends CRUDService<UserModel, UUID> {

    UserModel findByUsername(String username);

    UserModel findByEmail(String email);
}
