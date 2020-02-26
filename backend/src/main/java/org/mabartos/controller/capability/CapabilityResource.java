package org.mabartos.controller.capability;

import org.mabartos.api.model.BartSession;
import org.mabartos.persistence.model.CapabilityModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class CapabilityResource {

    private final BartSession session;

    public CapabilityResource(BartSession session) {
        this.session = session;
    }

    @GET
    public CapabilityModel getCapability() {
        return session.getActualCapability();
    }

    @PATCH
    public CapabilityModel updateDevice(@Valid CapabilityModel capability) {
        return session.services().capabilities().updateByID(session.getActualCapability().getID(), capability);
    }

    @DELETE
    public boolean deleteDevice() {
        return session.services().capabilities().deleteByID(session.getActualCapability().getID());
    }

}
