package org.mabartos.controller;

import org.mabartos.general.DeviceType;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.UserModel;
import org.mabartos.service.core.CRUDService;
import org.mabartos.service.core.HomeService;
import org.mabartos.streams.mqtt.BarMqttClient;
import org.mabartos.streams.mqtt.messages.MqttAddDeviceMessage;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
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
    private BarMqttClient client;


    @Inject
    public HomeResource(HomeService homeService, BarMqttClient client) {
        this.homeService = homeService;
        this.client = client;
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
    @Path("/mqtt/{idMqtt}")
    public void turnOnMqtt(@PathParam("idMqtt") Long id) {
        client.init("tcp://localhost:1883", getHomeByID(id));
    }

    @GET
    @Path("aaa")
    public String getJson(String json) {
        MqttAddDeviceMessage msg = MqttAddDeviceMessage.fromJson(json);
        System.out.println(msg);
        return msg.toJson();
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
