package org.mabartos.controller;

import io.smallrye.reactive.messaging.annotations.Channel;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jboss.resteasy.annotations.SseElementType;
import org.mabartos.streams.mqtt.BarMqttClient;
import org.reactivestreams.Publisher;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/streams")
@ApplicationScoped
public class StreamResource {

    private BarMqttClient client;

    @PostConstruct
    public void setUp() {
        client = new BarMqttClient("tcp://localhost:1883","input");
    }

    @Inject
    @Channel("result")
    Publisher<Integer> numbers;

    @POST
    @Path("/{num}")
    @Produces(MediaType.TEXT_PLAIN)
    public void publish(@PathParam("num") Integer num) {
        try {
            client.getMqttClient().publish("input", new MqttMessage(num.toString().getBytes()));

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Publisher<Integer> stream() {
        return numbers;
    }
}
