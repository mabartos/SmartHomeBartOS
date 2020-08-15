/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.controller.room;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.BartSession;
import org.mabartos.api.controller.device.DevicesResource;
import org.mabartos.api.controller.room.OwnerData;
import org.mabartos.api.controller.room.RoomResource;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.api.model.user.UserModel;
import org.mabartos.controller.device.DevicesResourceProvider;

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
public class RoomResourceProvider implements RoomResource {

    private final BartSession session;

    public RoomResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public RoomModel getRoom() {
        return session.services().rooms().findByID(session.getActualRoom().getID());
    }

    @PATCH
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public RoomModel updateRoom(String JSON) {
        return session.services().rooms().updateFromJson(session.getActualRoom().getID(), JSON);
    }

    @DELETE
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public boolean deleteRoom() {
        return session.services().rooms().deleteByID(session.getActualRoom().getID());
    }

    @GET
    @Path(OWNER_PATH)
    public Set<UserModel> getOwners() {
        return session.services().rooms().getOwners(session.getActualRoom().getID());
    }

    @GET
    @Path(OWNER_PATH + "/exists")
    public Response isOwner(OwnerData data) {
        if (data != null && session.services().rooms().isOwner(session.getActualRoom().getID(), data.getOwnerID())) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @POST
    @Path(OWNER_PATH)
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public Response addOwnerByID(OwnerData data) {
        if (data != null && session.services().rooms().addOwnerByID(session.getActualRoom().getID(), data.getOwnerID())) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @DELETE
    @Path(OWNER_PATH)
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public Response removeOwner(OwnerData data) {
        if (data != null && session.services().rooms().removeOwner(session.getActualRoom().getID(), data.getOwnerID())) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @Path(DevicesResource.PATH)
    public DevicesResource forwardToDevices() {
        return new DevicesResourceProvider(session);
    }
}
