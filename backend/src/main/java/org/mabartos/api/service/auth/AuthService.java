package org.mabartos.api.service.auth;

import org.mabartos.persistence.model.UserModel;

public interface AuthService {

    UserModel login(AuthData authData);
}
