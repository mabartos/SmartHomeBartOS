package org.mabartos.controller;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import org.mabartos.api.service.AppServices;
import org.mabartos.persistence.model.user.UserModel;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Provider
@Priority(Priorities.AUTHENTICATION)
@Authenticated
public class CheckNewUserFilter implements ContainerRequestFilter {

    @Inject
    AppServices services;

    @Inject
    SecurityIdentity securityIdentity;

    private UUID id;
    private String email;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            Principal basicPrincipal = requestContext.getSecurityContext().getUserPrincipal();
            if (!(basicPrincipal instanceof DefaultJWTCallerPrincipal))
                return;

            DefaultJWTCallerPrincipal principal = (DefaultJWTCallerPrincipal) basicPrincipal;
            id = UUID.fromString(principal.getClaim("sub"));
            email = principal.getClaim("email");

            if (!validEnvironment())
                return;

            if (!isUserExists()) {
                UserModel createUser = new UserModel();
                createUser.setID(id);
                createUser.setUsername(principal.getName());
                createUser.setEmail(email);
                services.users().create(createUser);
                return;
            } else {
                UserModel user = services.users().findByID(id);
                if (user != null) {
                    boolean shouldUpdate = false;
                    if (!user.getName().equals(principal.getName())) {
                        user.setUsername(principal.getName());
                        shouldUpdate = true;
                    }

                    if (!user.getEmail().equals(email)) {
                        user.setEmail(email);
                        shouldUpdate = true;
                    }

                    if (shouldUpdate) {
                        services.users().updateByID(user.getID(), user);
                    }
                }
            }
        } catch (ConstraintViolationException e) {
        }
    }

    private boolean validEnvironment() {
        return (securityIdentity != null && id != null && email != null);
    }

    private boolean isUserExists() {
        return (id != null && services.users().findByID(id) != null);
    }

    private Principal getAuthUser() {
        if (securityIdentity != null) {
            return securityIdentity.getPrincipal();
        }
        return null;
    }
}