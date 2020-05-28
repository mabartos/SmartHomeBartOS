/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.home.invitations;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.model.home.HomeInvitationModel;

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

@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface HomeInvitationsResource {
    String INVITE_ID_NAME = "idInvite";
    String INVITE_ID = "/{" + INVITE_ID_NAME + ":[\\d]+}";
    String INVITE_PATH = "/invitations";

    @GET
    Set<HomeInvitationModel> getInvitations();

    @POST
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN)
    Response createInvitationFromJSON(String JSON);

    @Path(INVITE_ID)
    HomeInvitationResource forwardToInvitation(@PathParam(INVITE_ID_NAME) Long id);
}
