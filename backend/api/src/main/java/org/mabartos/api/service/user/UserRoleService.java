/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.user;

import org.mabartos.api.controller.user.UserRoleData;
import org.mabartos.api.model.user.UserRoleModel;
import org.mabartos.api.service.CRUDService;

import java.util.Set;
import java.util.UUID;

public interface UserRoleService extends CRUDService<UserRoleModel, Long> {

    Set<UserRoleModel> getAllUserRoles(UUID userID);

    Set<UserRoleData> getAllUserRolesData(UUID userID);

    int deleteAllRolesFromHome(Long homeID);
}
