package org.mabartos.api.controller.capability;

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
public interface CapabilitiesResource {

    String CAP_ID_NAME = "idCaps";
    String CAP_ID = "/{" + CAP_ID_NAME + ":[\\d]+}";

    @GET
    Set<CapabilityModel> getCapabilities();

    @POST
    CapabilityModel createCapability(@Valid CapabilityModel capability) ;

    @Path(CAP_ID)
    CapabilityResource forwardToCapability(@PathParam(CAP_ID_NAME) Long id);
}
