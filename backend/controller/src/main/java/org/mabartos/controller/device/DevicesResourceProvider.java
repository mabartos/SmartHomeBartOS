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
import org.mabartos.api.controller.device.DeviceResource;
import org.mabartos.api.controller.device.DevicesResource;
import org.mabartos.api.data.general.device.DeviceData;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.controller.utils.ControllerUtil;

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
public class DevicesResourceProvider implements DevicesResource {
    private final BartSession session;

    public DevicesResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<DeviceModel> getAll() {
        return session.getActualRoom() != null ? session.getActualRoom().getChildren() : session.services().devices().getAll();
    }

    @Override
    public DeviceData createDeviceJSON(DeviceData data) {
        HomeModel home = session.getActualHome();
        RoomModel room = session.getActualRoom();

        if (home != null) {
            if (room != null) {
                return session.services().devices().createToRoomJSON(data, room.getID());
            } else {
                return session.services().devices().createToHomeJSON(data, home.getID());
            }
        }
        return null;
    }

    @POST
    @Path(ID_PATH + ADD_PATH)
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public DeviceModel addDeviceToRoom(@PathParam(ID_NAME) Long id) {
        return session.services().devices().addDeviceToRoom(session.getActualRoom().getID(), id);
    }

    @POST
    @Path(ID_PATH + REMOVE_PATH)
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public Response removeDeviceFromRoom(@PathParam(ID_NAME) Long id) {
        if (session.services().devices().removeDeviceFromRoom(session.getActualRoom().getID(), id)) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @Path(ID_PATH)
    public DeviceResource forwardToDevice(@PathParam(ID_NAME) Long id) {
        if (session.getActualRoom() == null || ControllerUtil.containsItem(session.getActualRoom().getChildren(), id)) {
            return new DeviceResourceProvider(session.setActualDevice(id));
        }
        return null;
    }
}
