package org.mabartos.controller;

import org.mabartos.api.model.BartSession;
import org.mabartos.api.service.UserService;
import org.mabartos.persistence.model.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class UserResource {

    private static final String USER_ID_NAME = "idUser";
    public static final String USER_ID = "/{" + USER_ID_NAME + ":[\\d]+}";

    private UserService userService;

    @Inject
    public UserResource(BartSession session) {
        this.userService = session.users();
    }

    @GET
    @Path(USER_ID)
    public UserModel getUserByID(@PathParam(USER_ID_NAME) Long id) {
        return userService.findByID(id);
    }

    @GET
    public Set<UserModel> getAll() {
        return userService.getAll();
    }

    @GET
    @Path("/search")
    public UserModel getUserByEmail(@QueryParam("email") String email) {
        if (email != null)
            return userService.findByEmail(email);
        return null;
    }

    @GET
    @Path("/search")
    public UserModel getUserByUsername(@QueryParam("username") String username) {
        if (username != null)
            return userService.findByUsername(username);
        return null;
    }

    @POST
    public UserModel createUser(@Valid UserModel user) {
        return userService.create(user);
    }

    @PATCH
    @Path(USER_ID)
    public UserModel updateUser(@PathParam(USER_ID_NAME) Long id, @Valid UserModel user) {
        return userService.updateByID(id, user);
    }

    @DELETE
    @Path(USER_ID)
    public boolean deleteUser(@PathParam(USER_ID_NAME) Long id) {
        return userService.deleteByID(id);
    }

    @Path(USER_ID + HomeResource.HOME_PATH)
    public HomeResource forwardToHome(@PathParam(USER_ID_NAME) Long id) {
        return new HomeResource(userService.findByID(id));
    }
}
