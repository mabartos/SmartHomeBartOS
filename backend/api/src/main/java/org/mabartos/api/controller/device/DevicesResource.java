package org.mabartos.api.controller.device;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
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

    String DEVICE_ID_NAME = "idDevice";
    String DEVICE_ID = "/{" + DEVICE_ID_NAME + ":[\\d]+}";
    String DEVICE_PATH = "/devices";

    @GET
    Set<DeviceModel> getAll();

    @POST
    @Path(DEVICE_ID + "/add")
    @HasRoleInHome(minRole = UserRole.HOME_MEMBER, orIsOwner = true)
    DeviceModel addDeviceToRoom(@PathParam(DEVICE_ID_NAME) Long id);

    @POST
    @Path(DEVICE_ID + "/remove")
    @HasRoleInHome(minRole = UserRole.HOME_MEMBER, orIsOwner = true)
    Response removeDeviceFromRoom(@PathParam(DEVICE_ID_NAME) Long id);

    @Path(DEVICE_ID)
    DeviceResource forwardToDevice(@PathParam(DEVICE_ID_NAME) Long id);
}
