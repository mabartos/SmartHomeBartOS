/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.device;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.capability.CapabilitiesResource;
import org.mabartos.api.data.general.device.AddDeviceToRoomData;
import org.mabartos.api.data.general.device.ConnectData;
import org.mabartos.api.model.device.DeviceModel;

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
@HasRoleInHome
public interface DeviceResource {
    String CAPABILITY = "/caps";

    String CONNECT_PATH = "/connect";
    String DISCONNECT_PATH = "/connect";

    String ADD_TO_ROOM = "/add-to-room";
    String REMOVE_FROM_ROOM = "/remove-from-room";

    @GET
    DeviceModel getDevice();

    @GET
    @Path(CONNECT_PATH)
    ConnectData connectDeviceJSON();

    @GET
    @Path(DISCONNECT_PATH)
    boolean disconnectDevice();

    @GET
    @Path(ADD_TO_ROOM)
    DeviceModel addToRoom();

    @GET
    @Path(REMOVE_FROM_ROOM)
    boolean removeFromRoom();

    @PATCH
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    DeviceModel updateDevice(String JSON);

    @DELETE
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    Response deleteDevice();

    @Path(CAPABILITY)
    CapabilitiesResource forwardToCapabilities();

}
