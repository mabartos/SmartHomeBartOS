/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.controller.device;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.BartSession;
import org.mabartos.api.controller.capability.CapabilitiesResource;
import org.mabartos.api.controller.device.DeviceResource;
import org.mabartos.api.data.general.device.ConnectData;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.controller.capability.CapabilitiesResourceProvider;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class DeviceResourceProvider implements DeviceResource {

    private final BartSession session;

    public DeviceResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public DeviceModel getDevice() {
        return session.getActualDevice();
    }

    @GET
    @Path(CONNECT_PATH)
    public ConnectData connectDeviceJSON() {
        return session.services()
                .devices()
                .connectDevice(session.getActualDevice().getID());
    }

    @GET
    @Path(DISCONNECT_PATH)
    public boolean disconnectDevice() {
        return session.services()
                .devices()
                .disconnectDevice(session.getActualDevice().getID());
    }

    @GET
    @Path(ADD_TO_ROOM)
    public DeviceModel addToRoom() {
        RoomModel room = session.getActualRoom();
        if (room != null) {
            return session.services()
                    .devices()
                    .addDeviceToRoom(room.getID(), session.getActualDevice().getID());
        }
        return null;
    }

    @GET
    @Path(REMOVE_FROM_ROOM)
    public boolean removeFromRoom() {
        RoomModel room = session.getActualRoom();
        if (room != null) {
            return session.services()
                    .devices()
                    .removeDeviceFromRoom(room.getID(), session.getActualDevice().getID());
        }
        return false;
    }

    @PATCH
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public DeviceModel updateDevice(String JSON) {
        return session.services().devices().updateFromJson(session.getActualDevice().getID(), JSON);
    }

    @DELETE
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public Response deleteDevice() {
        if (session.services().devices().deleteByID(session.getActualDevice().getID())) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @Path(CAPABILITY)
    public CapabilitiesResource forwardToCapabilities() {
        return new CapabilitiesResourceProvider(session);
    }
}
