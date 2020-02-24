package org.mabartos.controller;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.model.BartSession;
import org.mabartos.api.service.HomeService;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.UserModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
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
import java.util.Set;
import java.util.logging.Logger;

@Path("/homes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@ApplicationScoped
public class HomeResource {
    public static Logger logger = Logger.getLogger(HomeResource.class.getName());

    private static final String HOME_ID_NAME = "idHome";
    private static final String HOME_ID = "/{" + HOME_ID_NAME + ":[\\d]+}";
    public static final String HOME_PATH = "/homes";

    private UserModel parent;
    private HomeService homeService;
    private BartSession session;

    public void initMqttClient(@Observes StartupEvent start) {
        homeService.getAll()
                .forEach(home -> {
                    session.getMqttClient().init(home.getBrokerURL(), home);
                    logger.info("MQTT client with broker: " + home.getBrokerURL() + " STARTED");
                });
    }

    @Inject
    public HomeResource(BartSession session) {
        this.session = session;
        this.homeService = session.homes();
    }

    public HomeResource(UserModel parent) {
        this.parent = parent;
        setParent();
    }

    @PostConstruct
    public void setParent() {
        if (this.parent != null)
            homeService.setParentModel(this.parent);
    }

    @GET
    @Path(HOME_ID + DeviceResource.DEVICE_PATH)
    public Set<DeviceModel> getDevices(@PathParam(HOME_ID_NAME) Long id) {
        return homeService.getAllUnAssignedDevices(id);
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
        return new RoomResource(homeService.findByID(id));
    }

}
