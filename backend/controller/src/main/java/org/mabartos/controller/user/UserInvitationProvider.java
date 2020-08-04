/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.controller.user;

import org.mabartos.api.controller.BartSession;
import org.mabartos.api.controller.home.invitations.HomeInvitationsResource;
import org.mabartos.api.controller.user.UserInvitationResource;
import org.mabartos.api.model.home.HomeInvitationModel;
import org.mabartos.api.model.user.UserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
public class UserInvitationProvider implements UserInvitationResource {

    private BartSession session;

    @Inject
    public UserInvitationProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<HomeInvitationModel> getInvitations() {
        UserModel userAuth = session.auth().getUserInfo();
        return (userAuth != null) ? userAuth.getInvitations() : null;
    }

    @GET
    @Path(HomeInvitationsResource.INVITE_ID)
    public HomeInvitationModel getInvitation(@PathParam(HomeInvitationsResource.INVITE_ID_NAME) Long id) {
        return session.services().homes().invitations().findByID(id);
    }

    @GET
    @Path(HomeInvitationsResource.INVITE_ID + ACCEPT_NAME)
    public Response acceptInvitation(@PathParam(HomeInvitationsResource.INVITE_ID_NAME) Long id) {
        UserModel user = session.auth().getUserInfo();
        HomeInvitationModel invitation = session.services().homes().invitations().findByID(id);
        if (user != null && invitation != null && session.services().homes().invitations().acceptInvitation(invitation.getID(), user)) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @GET
    @Path(HomeInvitationsResource.INVITE_ID + DISMISS_NAME)
    public Response dismissInvitation(@PathParam(HomeInvitationsResource.INVITE_ID_NAME) Long id) {
        UserModel user = session.auth().getUserInfo();
        HomeInvitationModel invitation = session.services().homes().invitations().findByID(id);
        if (user != null && invitation != null && session.services().homes().invitations().dismissInvitation(invitation.getID(), user)) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }
}
