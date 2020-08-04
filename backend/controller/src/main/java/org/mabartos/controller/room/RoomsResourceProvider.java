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
import org.mabartos.api.controller.room.RoomData;
import org.mabartos.api.controller.room.RoomResource;
import org.mabartos.api.controller.room.RoomsResource;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.services.utils.DataToModelBase;

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
public class RoomsResourceProvider implements RoomsResource {

    private final BartSession session;

    public RoomsResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<RoomModel> getAll() {
        return session.getActualHome().getChildren();
    }

    @POST
    @HasRoleInHome(minRole = UserRole.HOME_MEMBER)
    public RoomModel createRoom(String JSON) {
        RoomModel room = DataToModelBase.toRoomModel(RoomData.fromJson(JSON));
        room.setHome(session.getActualHome());
        return session.services().rooms().create(room);
    }

    @POST
    @Path(ROOM_ID + "/add")
    @HasRoleInHome(minRole = UserRole.HOME_MEMBER)
    public RoomModel addRoomToHome(@PathParam(ROOM_ID_NAME) Long id) {
        RoomModel room = session.services().rooms().findByID(id);
        if (room != null && session.getActualHome() != null) {
            room.setHome(session.getActualHome());
            session.getActualHome().addChild(room);
            return session.services().rooms().updateByID(id, room);
        }
        return null;
    }

    @Path(ROOM_ID)
    public RoomResource forwardToRoom(@PathParam(ROOM_ID_NAME) Long id) {
        if (session.getActualHome() == null || ControllerUtil.containsItem(session.getActualHome().getChildren(), id)) {
            return new RoomResourceProvider(session.setActualRoom(id));
        }
        return null;
    }
}
