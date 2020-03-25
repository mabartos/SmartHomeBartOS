package org.mabartos.controller.capability;

import org.mabartos.api.controller.capability.CapabilitiesResource;
import org.mabartos.api.controller.capability.CapabilityResource;
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
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class CapabilitiesResourceProvider implements CapabilitiesResource {

    private final BartSession session;

    public CapabilitiesResourceProvider(BartSession session) {
        this.session = session;
        this.session.initEnvironment();
    }

    @GET
    public Set<CapabilityModel> getCapabilities() {
        if (session.getActualDevice() != null)
            return session.getActualDevice().getCapabilities();
        return null;
    }

    @POST
    public CapabilityModel createCapability(@Valid CapabilityModel capability) {
        CapabilityModel created = session.services().capabilities().create(capability);
        if (created != null && session.getActualDevice() != null) {
            session.getActualDevice().addCapability(created);
            return session.services().capabilities().updateByID(created.getID(), created);
        }
        return null;
    }

    @Path(CAP_ID)
    public CapabilityResource forwardToCapability(@PathParam(CAP_ID_NAME) Long id) {
        if (session.getActualDevice() == null || ControllerUtil.containsItem(session.getActualDevice().getCapabilities(), id)) {
            return new CapabilityResourceProvider(session.setActualCapability(id));
        }
        return null;
    }
}
