package org.mabartos.controller.home.mqtt;

import org.mabartos.api.model.BartSession;
import org.mabartos.persistence.model.MqttClientModel;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class MqttResource {

    private final BartSession session;

    public MqttResource(BartSession session) {
        this.session = session;
    }

    @GET
    public MqttClientModel getInfo() {
        return session.getActualHome().getMqttClient();
    }
}
