package org.mabartos.utils;

import org.mabartos.general.UserRole;
import org.mabartos.persistence.model.UserModel;

import java.io.Serializable;
import java.util.HashSet;

public class DedicatedUserRoles implements Serializable {

    private HashSet<DedicatedUserRole> userRoles = new HashSet<>();

    public DedicatedUserRoles() {
    }

    public HashSet<DedicatedUserRole> getUserRoles() {
        return userRoles;
    }

    public boolean changeRoleForUser(UserModel user, UserRole role) {
        return userRoles.add(new DedicatedUserRole(user, role));
    }

    public boolean changeRoleForUser(DedicatedUserRole userRole) {
        return userRoles.add(userRole);
    }

    public boolean removeRoleForUser(UserModel user, UserRole role) {
        return userRoles.remove(new DedicatedUserRole(user, role));
    }

    public boolean removeRoleForUser(DedicatedUserRole userRole) {
        return userRoles.remove(userRole);
    }


}
