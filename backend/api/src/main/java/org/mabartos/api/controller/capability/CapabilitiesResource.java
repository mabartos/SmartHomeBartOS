/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.capability;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.model.capability.CapabilityModel;

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
@HasRoleInHome
public interface CapabilitiesResource {

    String CAP_ID_NAME = "idCaps";
    String CAP_ID = "/{" + CAP_ID_NAME + ":[\\d]+}";

    @GET
    Set<CapabilityModel> getCapabilities();

    @POST
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    CapabilityModel createCapability(String JSON);

    @Path(CAP_ID)
    CapabilityResource forwardToCapability(@PathParam(CAP_ID_NAME) Long id);
}
