package org.mabartos.api.service.auth;

import org.mabartos.persistence.model.UserModel;

import java.security.Principal;

public interface AuthService {

    Principal getAuthUser();

    UserModel getUserInfo();
}
