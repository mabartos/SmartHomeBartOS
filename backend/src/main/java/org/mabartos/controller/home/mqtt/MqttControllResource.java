package org.mabartos.controller.home.mqtt;


import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.protocol.BartMqttClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
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
@ApplicationScoped
@Path("/mqtt")
public class MqttControllResource {

    @Inject
    BartMqttClient client;

    public MqttControllResource() {
    }

    public void start(@Observes StartupEvent start){}

    @GET
    @Path("/reconnect")
    public Response recconectClient() {
        return client.reconnectClient() ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}
