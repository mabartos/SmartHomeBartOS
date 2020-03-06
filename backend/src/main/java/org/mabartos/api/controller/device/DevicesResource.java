package org.mabartos.api.controller.device;

import org.mabartos.persistence.model.DeviceModel;

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

@Path("/devices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public interface DevicesResource {

    String DEVICE_ID_NAME = "idDevice";
    String DEVICE_ID = "/{" + DEVICE_ID_NAME + ":[\\d]+}";
    String DEVICE_PATH = "/devices";

    @GET
    Set<DeviceModel> getAll();

    @POST
    DeviceModel createDevice(@Valid DeviceModel device);


    @POST
    @Path(DEVICE_ID + "/add")
    DeviceModel addDeviceToRoom(@PathParam(DEVICE_ID_NAME) Long id);


    @Path(DEVICE_ID)
    DeviceResource forwardToDevice(@PathParam(DEVICE_ID_NAME) Long id);

}
