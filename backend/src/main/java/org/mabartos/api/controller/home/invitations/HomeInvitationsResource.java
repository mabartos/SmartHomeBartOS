package org.mabartos.api.controller.home.invitations;

import org.mabartos.persistence.model.HomeInvitationModel;

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

@Path("/invitations")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface HomeInvitationsResource {
    String INVITE_ID_NAME = "idInvite";
    String INVITE_ID = "/{" + INVITE_ID_NAME + ":[\\d]+}";
    String INVITE_PATH = "/invitations";

    @GET
    Set<HomeInvitationModel> getUsersInvitations();

    @GET
    @Path("/home")
    Set<HomeInvitationModel> getHomesInvitations();

    @POST
    HomeInvitationModel createInvitation(@Valid HomeInvitationModel invitation);

    @Path(INVITE_ID)
    HomeInvitationResource forwardToInvitation(@PathParam(INVITE_ID_NAME) Long id);
}
