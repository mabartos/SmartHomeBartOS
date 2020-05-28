/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.user;

import org.mabartos.api.controller.home.invitations.HomeInvitationsResource;
import org.mabartos.api.model.home.HomeInvitationModel;

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
public interface UserInvitationResource {

    public static final String ACCEPT_NAME = "/accept";
    public static final String DISMISS_NAME = "/dismiss";

    @GET
    Set<HomeInvitationModel> getInvitations();

    @GET
    @Path(HomeInvitationsResource.INVITE_ID)
    HomeInvitationModel getInvitation(@PathParam(HomeInvitationsResource.INVITE_ID_NAME) Long id);

    @GET
    @Path(HomeInvitationsResource.INVITE_ID + ACCEPT_NAME)
    Response acceptInvitation(@PathParam(HomeInvitationsResource.INVITE_ID_NAME) Long id);

    @GET
    @Path(HomeInvitationsResource.INVITE_ID + DISMISS_NAME)
    Response dismissInvitation(@PathParam(HomeInvitationsResource.INVITE_ID_NAME) Long id);
}
