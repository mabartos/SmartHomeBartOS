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
import org.mabartos.api.controller.capability.triggers.TriggersResource;
import org.mabartos.api.model.events.trigger.TriggerModel;
import org.mabartos.controller.utils.ControllerUtil;

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
public class TriggersResourceProvider implements TriggersResource {

    private final BartSession session;

    public TriggersResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public Set<TriggerModel> getAll() {
        return session.services()
                .capabilities()
                .triggers()
                .getAll(session.getActualCapability().getID());
    }

    @POST
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN, orIsOwner = true)
    public TriggerModel createTrigger(TriggerData data) {
        return session.services()
                .capabilities()
                .triggers().create(data);
    }

    @Path(TRIGGER_ID)
    public TriggerResource forwardToTrigger(@PathParam(TRIGGER_ID_NAME) Long id) {
        if (ControllerUtil.containsItem(session.getActualCapability().getTriggers(), id)) {
            return new TriggerResourceProvider(this.session.setActualTrigger(id));
        }
        return null;
    }
}
