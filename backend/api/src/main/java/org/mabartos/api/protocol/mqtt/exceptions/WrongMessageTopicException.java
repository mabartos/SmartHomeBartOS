/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.protocol.mqtt.exceptions;

public class WrongMessageTopicException extends RuntimeException {

    private final static String MESSAGE = "Wrong message with this topic.";

    public WrongMessageTopicException() {
        super(MESSAGE);
    }

    public WrongMessageTopicException(String msg) {
        super(msg);
    }

    public WrongMessageTopicException(Throwable rootCause) {
        super(MESSAGE, rootCause);
    }

    public WrongMessageTopicException(String msg, Throwable rootCause) {
        super(msg, rootCause);
    }
}
