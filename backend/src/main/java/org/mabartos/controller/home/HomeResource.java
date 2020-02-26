package org.mabartos.controller.home;

import org.mabartos.api.model.BartSession;
import org.mabartos.controller.device.DevicesResource;
import org.mabartos.controller.home.mqtt.MqttResource;
import org.mabartos.controller.room.RoomsResource;
import org.mabartos.controller.user.UsersResource;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class HomeResource {

    private final BartSession session;

    public HomeResource(BartSession session) {
        this.session = session;
    }

    @GET
    @Path(DevicesResource.DEVICE_PATH)
    public Set<DeviceModel> getDevices() {
        return session.getActualHome().getUnAssignedDevices();
    }

    @GET
    public HomeModel getHome() {
        return session.getActualHome();
    }

    @PATCH
    public HomeModel updateHome(@Valid HomeModel home) {
        return session.services().homes().updateByID(session.getActualHome().getID(), home);
    }

    @DELETE
    public boolean deleteHome() {
        return session.services().homes().deleteByID(session.getActualHome().getID());
    }

    @Path("/mqtt")
    public MqttResource forwardToMqttInfo() {
        return new MqttResource(session);
    }

    @Path(UsersResource.USER_PATH)
    public UsersResource forwardToUsers() {
        return new UsersResource(session);
    }

    @Path(RoomsResource.ROOM_PATH)
    public RoomsResource forwardToRooms() {
        return new RoomsResource(session);
    }
}
