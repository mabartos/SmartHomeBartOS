/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.controller.home.mqtt;

import org.mabartos.api.annotations.HasRoleInHome;
import org.mabartos.api.common.UserRole;
import org.mabartos.api.controller.BartSession;
import org.mabartos.api.controller.home.mqtt.MqttResource;
import org.mabartos.api.model.mqtt.MqttClientModel;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@HasRoleInHome
public class MqttResourceProvider implements MqttResource {

    private final BartSession session;

    public MqttResourceProvider(BartSession session) {
        this.session = session;
    }

    @GET
    public MqttClientModel getInfo() {
        return session.getActualHome().getMqttClient();
    }

    @GET
    @Path("/restart")
    @HasRoleInHome(minRole = UserRole.HOME_ADMIN)
    public Response restartMqttClient() {
        if (session.getClientManager().initClient(session.getActualHome().getID())) {
            return Response.status(200).build();
        }
        return Response.status(400).build();
    }
}
