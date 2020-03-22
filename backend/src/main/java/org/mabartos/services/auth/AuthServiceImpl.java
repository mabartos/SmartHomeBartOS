package org.mabartos.services.auth;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.service.UserService;
import org.mabartos.api.service.auth.AuthData;
import org.mabartos.api.service.auth.AuthService;
import org.mabartos.persistence.model.UserModel;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Dependent
public class AuthServiceImpl implements AuthService {

    @Inject
    UserService service;

    public void start(@Observes StartupEvent event) {
    }

    @Override
    public UserModel login(AuthData authData) {
        if (authData != null) {
            UserModel user = service.findByUsername(authData.getUsername());
            return (user != null && user.getPassword().equals(authData.getPassword())) ? user : null;
        }
        return null;
    }
}
