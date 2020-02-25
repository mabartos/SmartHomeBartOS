package org.mabartos.controller;

import org.mabartos.api.model.BartSession;
import org.mabartos.controller.utils.ControllerUtil;
import org.mabartos.persistence.model.CapabilityModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class CapabilitiesResource {
    private static final String CAP_ID_NAME = "idCaps";
    private static final String CAP_ID = "/{" + CAP_ID_NAME + ":[\\d]+}";

    private final BartSession session;

    public CapabilitiesResource(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<CapabilityModel> getCapabilities() {
        return session.getActualDevice().getCapabilities();
    }

    @POST
    public CapabilityModel createCapability(@Valid CapabilityModel capability) {
        CapabilityModel created = session.capabilities().create(capability);
        session.getActualDevice().addCapability(created);
        return session.capabilities().updateByID(created.getID(), created);
    }

    @Path(CAP_ID)
    public CapabilityResource forwardToCapability(@PathParam(CAP_ID_NAME) Long id) {
        if (session.getActualDevice() == null || ControllerUtil.containsItem(session.getActualDevice().getCapabilities(), id)) {
            return new CapabilityResource(session.setActualCapability(id));
        }
        return null;
    }
}
