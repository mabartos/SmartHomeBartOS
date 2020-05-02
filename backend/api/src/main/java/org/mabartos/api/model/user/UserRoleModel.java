package org.mabartos.api.model.user;

import org.mabartos.api.common.Identifiable;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.model.home.HomeModel;

import java.util.UUID;

public interface UserRoleModel extends Identifiable<Long> {

    UserModel getUser();

    void setUser(UserModel user);

    HomeModel getHome();

    void setHome(HomeModel home);

    UserRole getRole();

    void setRole(UserRole role);

    /* COMPUTED */
    UUID getUserID();

    Long getHomeID();
}
