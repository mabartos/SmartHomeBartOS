package org.mabartos.api.service.auth;

import org.mabartos.persistence.model.UserModel;

import java.security.Principal;
import java.util.UUID;

public interface AuthService {

    void checkNewUser();

    Principal getAuthUser();

    UserModel getUserInfo();

    UUID getID();
}
