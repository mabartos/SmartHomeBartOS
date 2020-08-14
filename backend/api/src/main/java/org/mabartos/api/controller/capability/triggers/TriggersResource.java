/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.controller.capability.triggers;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.model.events.trigger.TriggerModel;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public interface TriggersResource {
    String TRIGGER_PATH = "/triggers";
    String TRIGGER_ID_NAME = "triggerID";
    String TRIGGER_ID = "/{" + TRIGGER_ID_NAME + ":[\\d]+}";

    @GET
    Set<TriggerModel> getAll();

    @POST
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    TriggerModel createTrigger(TriggerData data);

    @Path(TRIGGER_ID)
    TriggerResource forwardToTrigger(@PathParam(TRIGGER_ID_NAME) Long id);
}
