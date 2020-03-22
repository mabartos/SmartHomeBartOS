package org.mabartos.api.controller.auth;

import org.mabartos.api.service.auth.AuthData;

import javax.ws.rs.core.Response;


public interface AuthResource {

    Response login(AuthData authData);
}
