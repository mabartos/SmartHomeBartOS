package org.mabartos.controller;

import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.service.core.CRUDService;
import org.mabartos.service.core.RoomService;

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

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class RoomResource {

    private static final String ROOM_ID_NAME = "idRoom";
    private static final String ROOM_ID = "/{" + ROOM_ID_NAME + ":[\\d]+}";
    public static final String ROOM_PATH = "/rooms";

    private HomeModel parent;
    private Set<CRUDService> services;
    private RoomService roomService;

    @Inject
    public RoomResource(RoomService roomService) {
        this.roomService = roomService;
    }

    public RoomResource(HomeModel parent, Set<CRUDService> services) {
        this.parent = parent;
        this.services = services;
        this.roomService = (RoomService) services.stream()
                .filter(f -> f instanceof RoomService)
                .findFirst()
                .orElseThrow(NotFoundException::new);
        setParent();
    }

    @PostConstruct
    public void setParent() {
        if (this.parent != null)
            roomService.setParentModel(this.parent);
    }

    @GET
    @Path(ROOM_ID)
    public RoomModel getRoomByID(@PathParam(ROOM_ID_NAME) Long id) {
        return roomService.findByID(id);
    }

    @GET
    public Set<RoomModel> getAll() {
        return roomService.getAll();
    }

    @POST
    public RoomModel createRoom(@Valid RoomModel home) {
        return roomService.create(home);
    }

    @POST
    @Path(ROOM_ID)
    public boolean addRoomToHome(@PathParam(ROOM_ID_NAME) Long idRoom) {
        return roomService.addModelToParent(idRoom);
    }

    @PATCH
    @Path(ROOM_ID)
    public RoomModel updateRoom(@PathParam(ROOM_ID_NAME) Long id, @Valid RoomModel home) {
        return roomService.updateByID(id, home);
    }

    @DELETE
    @Path(ROOM_ID)
    public boolean deleteRoom(@PathParam(ROOM_ID_NAME) Long id) {
        return roomService.deleteByID(id);
    }

    @Path(ROOM_ID + DeviceResource.DEVICE_PATH)
    public DeviceResource forwardToRoom(@PathParam(ROOM_ID_NAME) Long id) {
        return new DeviceResource(roomService.findByID(id), services);
    }

}
