package org.mabartos.api.controller.device;

import org.mabartos.api.controller.capability.CapabilitiesResource;
import org.mabartos.authz.annotations.HasRoleInHome;
import org.mabartos.general.UserRole;
import org.mabartos.persistence.model.DeviceModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
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

    @GET
    DeviceModel getDevice();

    @PATCH
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    DeviceModel updateDevice(@Valid DeviceModel device);

    @DELETE
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    Response deleteDevice();

    @Path(CAPABILITY)
    CapabilitiesResource forwardToCapabilities();

}
