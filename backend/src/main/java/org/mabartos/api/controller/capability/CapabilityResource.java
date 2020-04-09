package org.mabartos.api.controller.capability;

import org.mabartos.authz.annotations.HasRoleInHome;
import org.mabartos.general.UserRole;
import org.mabartos.persistence.model.CapabilityModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
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
public interface CapabilityResource {

    @GET
    CapabilityModel getCapability();

    @PATCH
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    CapabilityModel updateCapability(@Valid CapabilityModel capability);

    @DELETE
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    Response deleteCapability();
}
