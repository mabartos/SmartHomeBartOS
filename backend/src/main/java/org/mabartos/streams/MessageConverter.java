package org.mabartos.streams;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageConverter {
    
    @Incoming("input")
    @Outgoing("result")
    @Broadcast
    public int process(int number) {
        return number + 100;
    }
}
