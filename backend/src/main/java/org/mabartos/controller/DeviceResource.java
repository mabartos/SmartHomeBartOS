package org.mabartos.controller;

import org.mabartos.api.model.BartSession;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.persistence.model.CapabilityModel;
import org.mabartos.persistence.model.DeviceModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class DeviceResource {
    public static final String CAPABILITY = "/caps";

    private final BartSession session;

    public DeviceResource(BartSession session) {
        this.session = session;
    }

    @GET
    public DeviceModel getDevice() {
        return session.getActualDevice();
    }

    @PATCH
    public DeviceModel updateDevice(@Valid DeviceModel device) {
        return session.devices().updateByID(session.getActualDevice().getID(), device);
    }

    @DELETE
    public boolean deleteDevice() {
        return session.devices().deleteByID(session.getActualDevice().getID());
    }

    @Path(CAPABILITY)
    public CapabilitiesResource forwardToCapabilities() {
        return new CapabilitiesResource(session);
    }
}
