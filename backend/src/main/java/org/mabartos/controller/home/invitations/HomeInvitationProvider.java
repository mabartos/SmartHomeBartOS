package org.mabartos.controller.home.invitations;

import org.mabartos.api.controller.home.invitations.HomeInvitationResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.persistence.model.HomeInvitationModel;

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
public class HomeInvitationProvider implements HomeInvitationResource {

    private BartSession session;

    public HomeInvitationProvider(BartSession session) {
        this.session = session;
        this.session.initEnvironment();
    }

    @GET
    public HomeInvitationModel getInvitation() {
        return session.getActualInvitation();
    }

    @PATCH
    public HomeInvitationModel update(String JSON) {
        return session.services().invitations().updateFromJSON(session.getActualInvitation().getID(), JSON);
    }

    @DELETE
    public Response delete() {
        if (session.services().invitations().deleteByID(session.getActualInvitation().getID())) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }
}
