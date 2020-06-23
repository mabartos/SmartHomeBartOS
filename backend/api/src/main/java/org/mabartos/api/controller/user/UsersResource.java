/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.user;

import io.quarkus.security.Authenticated;
import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.model.user.UserModel;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@Authenticated
public interface UsersResource {
    String USER_ID_NAME = "idUser";
    String USER_ID = "/{" + USER_ID_NAME + ":[\\d]+}";
    String USER_PATH = "/users";

    @GET
    @HasRoleInHome(minRole = UserRole.SYS_ADMIN)
    Set<UserModel> getAll();

    @GET
    @Path("/searchEmail/{email}")
    UserModel getUserByEmail(@PathParam("email") String email);

    @GET
    @Path("/searchUsername/{username}")
    UserModel getUserByUsername(@PathParam("username") String username);

    @GET
    @Path("/search/{name}")
    List<UserModel> findUsersByNameOrEmail(@PathParam("name") String name);

    @Path(USER_ID)
    UserResource forwardToUser(@PathParam(USER_ID_NAME) UUID id);
}
