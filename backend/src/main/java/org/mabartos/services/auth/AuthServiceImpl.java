package org.mabartos.services.auth;

import io.quarkus.security.identity.SecurityIdentity;
import org.mabartos.api.service.UserService;
import org.mabartos.api.service.auth.AuthService;
import org.mabartos.persistence.model.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.security.Principal;

@RequestScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    UserService userService;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    public AuthServiceImpl() {
        checkNewUser();
    }

    private void checkNewUser() {
        UserModel user = getUserInfo();
        if (securityIdentity != null && user == null) {
            UserModel createUser = new UserModel();
            // createUser.setID(securityIdentity.getAttribute("id"));
            // createUser.setUsername(securityIdentity.getAttribute("username"));
            // createUser.setEmail(securityIdentity.getAttribute("email"));
            userService.create(createUser);
        }
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

    private String getPrincipalName() {
        if (securityIdentity != null && securityIdentity.getPrincipal() != null) {
            return securityIdentity.getPrincipal().getName();
        }
        return null;
    }
}
