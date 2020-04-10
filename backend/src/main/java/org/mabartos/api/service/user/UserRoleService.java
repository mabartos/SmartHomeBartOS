package org.mabartos.api.service.user;

import org.mabartos.api.service.CRUDService;
import org.mabartos.persistence.model.user.UserRoleModel;

import java.util.Set;
import java.util.UUID;

public interface UserRoleService extends CRUDService<UserRoleModel, Long> {

    Set<UserRoleModel> getAllUserRoles(UUID userID);

    int deleteAllRolesFromHome(Long homeID);
}
