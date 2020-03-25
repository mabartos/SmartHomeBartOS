package org.mabartos.services.auth;

import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.mabartos.api.service.UserService;
import org.mabartos.api.service.auth.AuthService;
import org.mabartos.persistence.model.UserModel;

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
    @Claim(standard = Claims.email)
    JsonString email;

    @Inject
    public AuthServiceImpl() {
    }

    @Override
    public void checkNewUser() {
        if (ableToCreateNewUser(getUserInfo())) {
            UserModel createUser = new UserModel();
            createUser.setID(UUID.fromString(id.getString()));
            createUser.setUsername(securityIdentity.getPrincipal().getName());
            createUser.setEmail(email.getString());
            userService.create(createUser);
        }
    }

    private boolean ableToCreateNewUser(UserModel user) {
        return (securityIdentity != null && user == null && id != null && email != null);
    }

    @Override
    public Principal getAuthUser() {
        if (securityIdentity != null) {
            return securityIdentity.getPrincipal();
        }
        return null;
    }

    @Override
    public UserModel getUserInfo() {
        String username = getPrincipalName();
        if (username != null) {
            return userService.findByUsername(username);
        }
        return null;
    }

    @Override
    public String getID() {
        return id.getString();
    }

    private String getPrincipalName() {
        if (securityIdentity != null && securityIdentity.getPrincipal() != null) {
            return securityIdentity.getPrincipal().getName();
        }
        return null;
    }
}
