package org.mabartos.api.controller.device;

import org.mabartos.api.controller.capability.CapabilitiesResource;
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
public interface DeviceResource {
    String CAPABILITY = "/caps";

    @GET
    DeviceModel getDevice();

    @PATCH
    DeviceModel updateDevice(@Valid DeviceModel device);

    @DELETE
    Response deleteDevice();

    @Path(CAPABILITY)
    CapabilitiesResource forwardToCapabilities();

}
