package org.mabartos.controller.home.invitations;

import org.mabartos.api.controller.home.invitations.HomeInvitationResource;
import org.mabartos.api.model.BartSession;
import org.mabartos.authz.annotations.HasRoleInHome;
import org.mabartos.general.UserRole;
import org.mabartos.persistence.model.home.HomeInvitationModel;
import org.mabartos.persistence.model.user.UserModel;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@HasRoleInHome
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

    @GET
    @Path("/accept")
    public Response acceptInvitation() {
        UserModel user = session.auth().getUserInfo();
        if (user != null && session.services().homes().invitations().acceptInvitation(session.getActualInvitation().getID(), user)) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @GET
    @Path("/dismiss")
    public Response dismissInvitation() {
        UserModel user = session.auth().getUserInfo();
        if (user != null && session.services().homes().invitations().dismissInvitation(session.getActualInvitation().getID(), user)) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @PATCH
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN)
    public HomeInvitationModel update(String JSON) {
        return session.services().homes().invitations().updateFromJSON(session.getActualInvitation().getID(), JSON);
    }

    @DELETE
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN)
    public Response delete() {
        if (session.services().homes().invitations().deleteByID(session.getActualInvitation().getID())) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }
}
