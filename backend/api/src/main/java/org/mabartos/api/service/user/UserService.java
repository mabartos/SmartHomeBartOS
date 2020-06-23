/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.user;

import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.service.CRUDService;

import java.util.List;
import java.util.UUID;

public interface UserService extends CRUDService<UserModel, UUID> {

    UserRoleService roles();

    UserModel findByUsername(String username);

    UserModel findByEmail(String email);

    List<UserModel> findAllByNameOrEmail(String nameOrEmail);
}
