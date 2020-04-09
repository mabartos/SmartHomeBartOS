package org.mabartos.api.controller.home;

import io.quarkus.security.Authenticated;
import org.mabartos.authz.annotations.HasRoleInHome;
import org.mabartos.general.UserRole;
import org.mabartos.persistence.model.home.HomeModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/homes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@Authenticated
public interface HomesResource {

    String HOME_ID_NAME = "homeID";
    String HOME_ID = "/{" + HOME_ID_NAME + ":[\\d]+}";
    String HOME_PATH = "/homes";

    @GET
    Response getAll();

    @POST
    HomeModel createHome(@Valid HomeModel home);

    @POST
    @Path("/add" + HOME_ID)
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN)
    HomeModel addHomeToUser(@PathParam(HOME_ID_NAME) Long id);

    @Path(HOME_ID)
    HomeResource forwardToHome(@PathParam(HOME_ID_NAME) Long id);
}
