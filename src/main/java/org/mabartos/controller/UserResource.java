package org.mabartos.controller;

import org.mabartos.persistence.model.UserModel;
import org.mabartos.service.core.UserService;

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
import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Transactional
public class UserResource {

    public static final String USER_ID = "/{idUser:[\\d]+}";

    private UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path(USER_ID)
    public UserModel getUserByID(@PathParam(USER_ID) long id) {
        return userService.findByID(id);
    }

    @GET
    public List<UserModel> getAll() {
        return userService.getAll();
    }

    //TODO, just testing
    @GET
    @Path("/search")
    public UserModel getUserByEmail(@QueryParam("email") String email, @QueryParam("username") String username) {
        if (email != null) {
            if (username != null && userService.findByUsername(username).email.equals(email))
                return userService.findByUsername(username);
            return userService.findByEmail(email);
        }

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
    public UserModel updateUser(@PathParam(USER_ID) Long id, @Valid UserModel user) {
        return userService.updateByID(id, user);
    }

    @DELETE
    @Path(USER_ID)
    public boolean deleteUser(@PathParam(USER_ID) Long id) {
        return userService.deleteByID(id);
    }

    @Path("/users/" + USER_ID + "/homes")
    public HomeResource forwardToHome() {
        return new HomeResource();
    }
}
