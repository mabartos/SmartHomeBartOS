package org.mabartos.controller;

import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.UserModel;
import org.mabartos.service.core.CRUDService;
import org.mabartos.service.core.HomeService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/homes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class HomeResource {

    private static final String HOME_ID_NAME = "idHome";
    private static final String HOME_ID = "/{" + HOME_ID_NAME + ":[\\d]+}";
    public static final String HOME_PATH = "/homes";

    private UserModel parent;
    private Set<CRUDService> services;
    private HomeService homeService;

    @Inject
    public HomeResource(HomeService homeService) {
        this.homeService = homeService;
    }

    public HomeResource(UserModel parent, Set<CRUDService> services) {
        this.parent = parent;
        this.services = services;
        this.homeService = (HomeService) services.stream()
                .filter(f -> f instanceof HomeService)
                .findFirst()
                .orElseThrow(NotFoundException::new);
        setParent();
    }

    @PostConstruct
    public void setParent() {
        if (this.parent != null)
            homeService.setParentModel(this.parent);
    }

    @GET
    @Path(HOME_ID)
    public HomeModel getHomeByID(@PathParam(HOME_ID_NAME) Long id) {
        return homeService.findByID(id);
    }

    @GET
    public Set<HomeModel> getAll() {
        return homeService.getAll();
    }

    @POST
    public HomeModel createHome(@Valid HomeModel home) {
        return homeService.create(home);
    }

    @POST
    @Path(HOME_ID)
    public boolean addHomeToParent(@PathParam(HOME_ID_NAME) Long id) {
        return homeService.addModelToParent(id);
    }

    @PATCH
    @Path(HOME_ID)
    public HomeModel updateHome(@PathParam(HOME_ID_NAME) Long id, @Valid HomeModel home) {
        return homeService.updateByID(id, home);
    }

    @DELETE
    @Path(HOME_ID)
    public boolean deleteHome(@PathParam(HOME_ID_NAME) Long id) {
        return homeService.deleteByID(id);
    }

    @Path(HOME_ID + RoomResource.ROOM_PATH)
    public RoomResource forwardToRoom(@PathParam(HOME_ID_NAME) Long id) {
        return new RoomResource(homeService.findByID(id), services);
    }

}
