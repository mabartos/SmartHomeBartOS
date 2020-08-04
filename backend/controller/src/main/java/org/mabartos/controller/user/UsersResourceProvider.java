/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.controller.user;

import io.quarkus.security.Authenticated;
import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.BartSession;
import org.mabartos.api.controller.user.UsersResource;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.controller.utils.ControllerUtil;

import javax.enterprise.context.RequestScoped;
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

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
@Authenticated
public class UsersResourceProvider implements UsersResource {

    private final BartSession session;

    public UsersResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    @HasRoleInHome(minRole = UserRole.SYS_ADMIN)
    public Set<UserModel> getAll() {
        return session.services().users().getAll();
    }

    @GET
    @Path("/searchEmail/{email}")
    public UserModel getUserByEmail(@PathParam("email") String email) {
        if (email != null)
            return session.services().users().findByEmail(email);
        return null;
    }

    @GET
    @Path("/searchUsername/{username}")
    public UserModel getUserByUsername(@PathParam("username") String username) {
        if (username != null)
            return session.services().users().findByUsername(username);
        return null;
    }

    @GET
    @Path("/search/{name}")
    public List<UserModel> findUsersByNameOrEmail(String name) {
        if (name != null) {
            return session.services().users().findAllByNameOrEmail(name);
        }
        return null;
    }

    @Path(USER_ID)
    public UserResourceProvider forwardToUser(@PathParam(USER_ID_NAME) UUID id) {
        if (ControllerUtil.containsItem(session.getActualHome().getUsers(), id)) {
            return new UserResourceProvider(session.setActualUser(id));
        }
        return null;
    }
}
