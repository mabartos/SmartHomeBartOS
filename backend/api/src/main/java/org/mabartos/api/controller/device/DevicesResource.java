/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.device;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.data.general.device.DeviceData;
import org.mabartos.api.model.device.DeviceModel;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@HasRoleInHome
public interface DevicesResource {

    String ID_NAME = "idDevice";
    String ID_PATH = "/{" + ID_NAME + ":[\\d]+}";
    String PATH = "/devices";

    String CREATE_PATH = "/create";
    String ADD_PATH = "/add";
    String REMOVE_PATH = "/remove";

    @GET
    Set<DeviceModel> getAll();

    @POST
    @Path(CREATE_PATH)
    DeviceData createDeviceJSON(DeviceData data);

    @POST
    @Path(ID_PATH + ADD_PATH)
    @HasRoleInHome(minRole = UserRole.HOME_MEMBER, orIsOwner = true)
    DeviceModel addDeviceToRoom(@PathParam(ID_NAME) Long id);

    @POST
    @Path(ID_PATH + REMOVE_PATH)
    @HasRoleInHome(minRole = UserRole.HOME_MEMBER, orIsOwner = true)
    Response removeDeviceFromRoom(@PathParam(ID_NAME) Long id);

    @Path(ID_PATH)
    DeviceResource forwardToDevice(@PathParam(ID_NAME) Long id);
}
