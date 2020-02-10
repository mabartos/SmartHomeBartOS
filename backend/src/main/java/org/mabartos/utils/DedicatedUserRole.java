package org.mabartos.utils;

import org.mabartos.general.UserRole;
import org.mabartos.persistence.model.UserModel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

public class DedicatedUserRole implements Serializable {

    private UserModel user;
    private UserRole role;

    public DedicatedUserRole(UserModel user, UserRole role) {
        this.user = user;
        this.role = role;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof DedicatedUserRole))
            return false;
        else {
            DedicatedUserRole object = (DedicatedUserRole) obj;
            return (object.getUser().equals(this.getUser()) && object.getRole().equals(this.getRole()));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}

