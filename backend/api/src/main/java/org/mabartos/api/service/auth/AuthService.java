package org.mabartos.api.service.auth;

import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import org.mabartos.api.model.user.UserModel;

import java.security.Principal;
import java.util.UUID;

public interface AuthService {
    UserModel getUserInfo();

    UUID getID();

    Principal getBasicPrincipal();

    DefaultJWTCallerPrincipal getAdvancedPrincipal();
}
