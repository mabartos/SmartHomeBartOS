package org.mabartos.controller.device;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.capability.CapabilitiesResource;
import org.mabartos.api.controller.device.DeviceResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.controller.capability.CapabilitiesResourceProvider;

import javax.transaction.Transactional;
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
public class DeviceResourceProvider implements DeviceResource {

    private final BartSession session;

    public DeviceResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public DeviceModel getDevice() {
        return session.getActualDevice();
    }

    @PATCH
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public DeviceModel updateDevice(String JSON) {
        return session.services().devices().updateFromJson(session.getActualDevice().getID(), JSON);
    }

    @DELETE
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public Response deleteDevice() {
        if (session.services().devices().deleteByID(session.getActualDevice().getID())) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @Path(CAPABILITY)
    public CapabilitiesResource forwardToCapabilities() {
        return new CapabilitiesResourceProvider(session);
    }
}
