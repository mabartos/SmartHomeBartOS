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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@HasRoleInHome
public interface HomeInvitationResource {

    @GET
    HomeInvitationModel getInvitation();

    @PATCH
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN)
    HomeInvitationModel update(String JSON);

    @DELETE
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN)
    Response delete();
}
