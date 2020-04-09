package org.mabartos.api.service.auth;

import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import org.mabartos.persistence.model.user.UserModel;

import java.security.Principal;
import java.util.UUID;

public interface AuthService {

    void checkNewUser();

    Principal getAuthUser();

    UserModel getUserInfo();

    UUID getID();

    DefaultJWTCallerPrincipal getAdvancedPrincipal();
}
