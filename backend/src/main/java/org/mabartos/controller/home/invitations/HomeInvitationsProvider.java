package org.mabartos.controller.home.invitations;

import org.mabartos.api.controller.home.invitations.HomeInvitationResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.persistence.model.HomeInvitationModel;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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

import static org.mabartos.api.controller.home.invitations.HomeInvitationsResource.INVITE_ID;
import static org.mabartos.api.controller.home.invitations.HomeInvitationsResource.INVITE_ID_NAME;

@Path("/invitations")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class HomeInvitationsProvider {

    private BartSession session;

    @Inject
    public HomeInvitationsProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<HomeInvitationModel> getUsersInvitations() {
        UserModel user = session.auth().getUserInfo();
        if (user != null) {
            return user.getInvitations();
        }
        return null;
    }

    @GET
    @Path("/home")
    public Set<HomeInvitationModel> getHomesInvitations() {
        HomeModel home = session.getActualHome();
        if (home != null) {
            return home.getInvitations();
        }
        return null;
    }

    @POST
    public HomeInvitationModel createInvitation(@Valid HomeInvitationModel invitation) {
        return session.services().invitations().create(invitation);
    }

    @Path(INVITE_ID)
    public HomeInvitationResource forwardToInvitation(@PathParam(INVITE_ID_NAME) Long id) {
        return new HomeInvitationProvider(session.setActualInvitation(id));
    }
}
