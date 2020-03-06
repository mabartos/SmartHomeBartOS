package org.mabartos.api.controller.user;

import org.mabartos.persistence.model.UserModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
public interface UsersResource {
    String USER_ID_NAME = "idUser";
    String USER_ID = "/{" + USER_ID_NAME + ":[\\d]+}";
    String USER_PATH = "/users";

    @GET
    Set<UserModel> getAll();

    @GET
    @Path("/search")
    UserModel getUserByEmail(@QueryParam("email") String email);

    @GET
    @Path("/search")
    UserModel getUserByUsername(@QueryParam("username") String username);

    @POST
    UserModel createUser(@Valid UserModel user);

    @POST
    @Path(USER_ID + "/add")
    UserModel addUserToHome(@PathParam(USER_ID_NAME) Long id);

    @Path(USER_ID)
    UserResource forwardToUser(@PathParam(USER_ID_NAME) Long id);
}
