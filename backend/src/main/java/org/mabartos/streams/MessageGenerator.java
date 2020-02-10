package org.mabartos.streams;


import io.reactivex.Flowable;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class MessageGenerator {
    
    private Random rand = new Random();

    @Outgoing("generated")
    public Flowable<Integer> generate() {
        return Flowable.interval(10000, TimeUnit.SECONDS)
                .map(t -> rand.nextInt(1000));
    }
}
