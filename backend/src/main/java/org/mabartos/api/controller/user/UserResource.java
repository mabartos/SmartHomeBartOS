package org.mabartos.api.controller.user;

import org.mabartos.api.controller.home.HomesResource;
import org.mabartos.persistence.model.UserModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public interface UserResource {

    @GET
    UserModel getUser();

    @PATCH
    UserModel updateUser(@Valid UserModel user);

    @POST
    UserModel addUserToHome();

    @DELETE
    Response deleteUser();

    @Path(HomesResource.HOME_PATH)
    HomesResource forwardToHomes();
}
