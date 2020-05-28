/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.room;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.model.room.RoomModel;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@HasRoleInHome
public interface RoomsResource {
    String ROOM_ID_NAME = "idRoom";
    String ROOM_ID = "/{" + ROOM_ID_NAME + ":[\\d]+}";
    String ROOM_PATH = "/rooms";

    @GET
    Set<RoomModel> getAll();

    @POST
    @HasRoleInHome(minRole = UserRole.HOME_MEMBER)
    RoomModel createRoom(String JSON);

    @POST
    @HasRoleInHome(minRole = UserRole.HOME_MEMBER)
    @Path(ROOM_ID + "/add")
    RoomModel addRoomToHome(@PathParam(ROOM_ID_NAME) Long id);

    @Path(ROOM_ID)
    RoomResource forwardToRoom(@PathParam(ROOM_ID_NAME) Long id);
}
