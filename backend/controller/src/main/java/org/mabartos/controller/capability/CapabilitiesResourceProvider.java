/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.controller.capability;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.BartSession;
import org.mabartos.api.controller.capability.CapabilitiesResource;
import org.mabartos.api.controller.capability.CapabilityResource;
import org.mabartos.api.model.capability.CapabilityModel;
import org.mabartos.controller.utils.ControllerUtil;

import javax.transaction.Transactional;
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
    }

    @GET
    public Set<CapabilityModel> getCapabilities() {
        if (session.getActualDevice() != null)
            return session.getActualDevice().getCapabilities();
        return null;
    }

    @POST
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public CapabilityModel createCapability(String JSON) {
        CapabilityModel created = session.services().capabilities().createFromJSON(JSON);
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
