/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.room;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.device.DevicesResource;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.api.model.user.UserModel;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@HasRoleInHome
public interface RoomResource {
    public final static String OWNER_PATH = "/owners";

    @GET
    RoomModel getRoom();

    @PATCH
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    RoomModel updateRoom(String JSON);

    @DELETE
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    boolean deleteRoom();

    @GET
    @Path(OWNER_PATH)
    Set<UserModel> getOwners();

    @GET
    @Path(OWNER_PATH + "/exists")
    Response isOwner(OwnerData data);

    @POST
    @Path(OWNER_PATH)
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    Response addOwnerByID(OwnerData data);

    @DELETE
    @Path(OWNER_PATH)
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    Response removeOwner(OwnerData data);

    @Path(DevicesResource.PATH)
    DevicesResource forwardToDevices();
}
