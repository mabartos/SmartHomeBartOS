package org.mabartos.controller;

import org.mabartos.persistence.model.HomeModel;
import org.mabartos.service.core.HomeService;

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
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/homes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Transactional
public class HomeResource {

    public static final String HOME_ID = "/{idHome:[\\d]+}";

    private HomeService homeService;

    public HomeResource() {
    }

    @Inject
    public HomeResource(HomeService homeService) {
        this.homeService = homeService;
    }

    @GET
    @Path(HOME_ID)
    public HomeModel getUserByID(@PathParam(HOME_ID) long id) {
        return homeService.findByID(id);
    }

    @GET
    public List<HomeModel> getAll() {
        return homeService.getAll();
    }

    @POST
    public HomeModel createUser(@Valid HomeModel user) {
        return homeService.create(user);
    }

    @PATCH
    @Path(HOME_ID)
    public HomeModel updateUser(@PathParam(HOME_ID) Long id, @Valid HomeModel user) {
        return homeService.updateByID(id, user);
    }

    @DELETE
    @Path(HOME_ID)
    public boolean deleteUser(@PathParam(HOME_ID) Long id) {
        return homeService.deleteByID(id);
    }
}
