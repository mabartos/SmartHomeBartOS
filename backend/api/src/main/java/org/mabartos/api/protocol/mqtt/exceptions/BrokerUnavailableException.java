/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.protocol.mqtt.exceptions;

public class BrokerUnavailableException extends RuntimeException {
    public BrokerUnavailableException() {
        super();
    }

    public BrokerUnavailableException(String message) {
        super(message);
    }
}
