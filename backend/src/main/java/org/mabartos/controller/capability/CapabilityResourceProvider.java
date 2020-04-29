package org.mabartos.controller.capability;

import org.mabartos.api.controller.capability.CapabilityResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.authz.annotations.HasRoleInHome;
import org.mabartos.general.UserRole;
import org.mabartos.persistence.model.CapabilityModel;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class CapabilityResourceProvider implements CapabilityResource {

    private final BartSession session;

    public CapabilityResourceProvider(BartSession session) {
        this.session = session;

    }

    @GET
    public CapabilityModel getCapability() {
        return session.getActualCapability();
    }

    @PATCH
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public CapabilityModel updateCapability(String JSON) {
        return session.services().capabilities().updateFromJson(session.getActualCapability().getID(), JSON);
    }

    @DELETE
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public Response deleteCapability() {
        if (session.services().capabilities().deleteByID(session.getActualCapability().getID())) {
            return Response.status(200).build();
        }
        return Response.status(400).entity("Cannot delete device").build();
    }

}
