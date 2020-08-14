/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.controller.capability.triggers;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.BartSession;
import org.mabartos.api.controller.capability.triggers.TriggerData;
import org.mabartos.api.controller.capability.triggers.TriggerResource;
import org.mabartos.api.model.events.trigger.TriggerModel;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.core.Response;

public class TriggerResourceProvider implements TriggerResource {

    BartSession session;

    public TriggerResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public TriggerModel getTrigger() {
        return session.getActualTrigger();
    }

    @PATCH
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public TriggerModel updateTrigger(TriggerData data) {
        return session.services()
                .capabilities()
                .triggers()
                .update(session.getActualTrigger().getID(), data);
    }

    @DELETE
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public Response deleteTrigger() {
        if (session.services().capabilities().triggers().deleteByID(session.getActualTrigger().getID())) {
            return Response.ok().build();
        }
        return Response.status(400).build();
    }
}
