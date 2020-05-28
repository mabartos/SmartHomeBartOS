/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.services.auth;

import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.api.service.auth.AuthService;
import org.mabartos.api.service.user.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonString;
import java.security.Principal;
import java.util.UUID;

@RequestScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    UserService userService;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    @Claim(standard = Claims.sub)
    JsonString id;

    @Inject
    public AuthServiceImpl() {
    }

    @Override
    public UserModel getUserInfo() {
        return userService.findByID(getID());
    }

    @Override
    public UUID getID() {
        return UUID.fromString(id.getString());
    }

    @Override
    public Principal getBasicPrincipal() {
        if (securityIdentity != null && securityIdentity.getPrincipal() != null) {
            return securityIdentity.getPrincipal();
        }
        return null;
    }

    @Override
    public DefaultJWTCallerPrincipal getAdvancedPrincipal() {
        if (securityIdentity != null && securityIdentity.getPrincipal() instanceof DefaultJWTCallerPrincipal) {
            return (DefaultJWTCallerPrincipal) securityIdentity.getPrincipal();
        }
        return null;
    }
}
