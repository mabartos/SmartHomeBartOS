package org.mabartos.controller.home.invitations;

import org.mabartos.api.controller.home.invitations.HomeInvitationResource;
import org.mabartos.api.controller.home.invitations.HomeInvitationsResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.api.service.invitations.HomeInvitationConflictException;
import org.mabartos.persistence.model.HomeInvitationModel;
import org.mabartos.persistence.model.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/invitations")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class HomeInvitationsProvider implements HomeInvitationsResource {

    private BartSession session;

    @Inject
    public HomeInvitationsProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<HomeInvitationModel> getInvitations() {
        UserModel user = session.auth().getUserInfo();
        if (user != null) {
            return (session.getActualHome() != null) ? session.getActualHome().getInvitations() : user.getInvitations();
        }
        return null;
    }

    @POST
    public Response createInvitationFromJSON(String JSON) {
        try {
            UserModel user = session.auth().getUserInfo();
            if (user != null) {
                HomeInvitationModel invitation = session.services().invitations().createFromJSON(user, JSON);
                if (invitation != null) {
                    return Response.ok(invitation).build();
                }
            }
        } catch (HomeInvitationConflictException e) {
            return Response.status(409).build();
        }
        return Response.status(400).build();
    }

    @Path(INVITE_ID)
    public HomeInvitationResource forwardToInvitation(@PathParam(INVITE_ID_NAME) Long id) {
        return new HomeInvitationProvider(session.setActualInvitation(id));
    }
}
