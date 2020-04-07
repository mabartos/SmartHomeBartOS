package org.mabartos.api.controller.home.invitations;

import org.mabartos.persistence.model.HomeInvitationModel;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface HomeInvitationResource {

    @GET
    HomeInvitationModel getInvitation();

    @GET
    @Path("/accept")
    Response acceptInvitation();

    @GET
    @Path("/dismiss")
    Response dismissInvitation();

    @PATCH
    HomeInvitationModel update(String JSON);

    @DELETE
    Response delete();
}
