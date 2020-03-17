package org.mabartos.api.controller.home;

import org.mabartos.api.controller.device.DevicesResource;
import org.mabartos.api.controller.home.mqtt.MqttResource;
import org.mabartos.api.controller.room.RoomsResource;
import org.mabartos.api.controller.user.UsersResource;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.HomeModel;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.Set;

public interface HomeResource {

    @GET
    @Path(DevicesResource.DEVICE_PATH)
    Set<DeviceModel> getDevices();

    @GET
    HomeModel getHome();

    @PATCH
    HomeModel updateHome(String json);

    @DELETE
    Response deleteHome();

    @Path("/mqtt")
    MqttResource forwardToMqttInfo();

    @Path(UsersResource.USER_PATH)
    UsersResource forwardToUsers();

    @Path(RoomsResource.ROOM_PATH)
    RoomsResource forwardToRooms();
}
