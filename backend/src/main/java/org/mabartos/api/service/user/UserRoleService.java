package org.mabartos.api.service.user;

import org.mabartos.api.service.CRUDService;
import org.mabartos.persistence.model.user.UserRoleModel;

public interface UserRoleService extends CRUDService<UserRoleModel, Long> {

    int deleteAllRolesFromHome(Long homeID);
}
