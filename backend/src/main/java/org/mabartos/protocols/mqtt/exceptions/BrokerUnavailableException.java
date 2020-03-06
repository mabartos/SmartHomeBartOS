package org.mabartos.protocols.mqtt.exceptions;

public class BrokerUnavailableException extends RuntimeException {
    public BrokerUnavailableException() {
        super();
    }

    public BrokerUnavailableException(String message) {
        super(message);
    }
}
