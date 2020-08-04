/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.controller.home.invitations;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.BartSession;
import org.mabartos.api.controller.home.invitations.HomeInvitationResource;
import org.mabartos.api.model.home.HomeInvitationModel;

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
@HasRoleInHome
public class HomeInvitationProvider implements HomeInvitationResource {

    private BartSession session;

    public HomeInvitationProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public HomeInvitationModel getInvitation() {
        return session.getActualInvitation();
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
