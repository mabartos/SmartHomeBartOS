package org.mabartos.api.controller.room;

import org.mabartos.api.controller.device.DevicesResource;
import org.mabartos.persistence.model.RoomModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public interface RoomResource {

    @GET
    RoomModel getRoom();

    @PATCH
    RoomModel updateRoom(@Valid RoomModel room);

    @DELETE
    boolean deleteRoom();

    @Path(DevicesResource.DEVICE_PATH)
    DevicesResource forwardToDevices();
}
