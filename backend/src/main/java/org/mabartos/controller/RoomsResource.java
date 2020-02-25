package org.mabartos.controller;

import org.mabartos.api.model.BartSession;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.model.UserModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
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
public class RoomsResource {
    private static final String ROOM_ID_NAME = "idRoom";
    private static final String ROOM_ID = "/{" + ROOM_ID_NAME + ":[\\d]+}";
    public static final String ROOM_PATH = "/rooms";

    private final BartSession session;

    public RoomsResource(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<RoomModel> getAll() {
        return session.getActualHome().getChildren();
    }

    @POST
    public RoomModel createRoom(@Valid RoomModel room) {
        room.setHome(session.getActualHome());
        return session.rooms().create(room);
    }

    @POST
    @Path(ROOM_ID + "/add")
    public RoomModel addRoomToHome(@PathParam(ROOM_ID_NAME) Long id) {
        RoomModel room = session.rooms().findByID(id);
        if (room != null && session.getActualHome() != null) {
            room.setHome(session.getActualHome());
            session.getActualHome().addChild(room);
            return session.rooms().updateByID(id, room);
        }
        return null;
    }

    @Path(ROOM_ID)
    public RoomResource forwardToRoom(@PathParam(ROOM_ID_NAME) Long id) {
        if (session.getActualHome() == null || ControllerUtil.containsItem(session.getActualHome().getChildren(), id)) {
            return new RoomResource(session.setActualRoom(id));
        }
        return null;
    }
}
