/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.protocol.mqtt.exceptions;

public class DeviceConflictException extends RuntimeException {

    public DeviceConflictException() {
        super();
    }

    public DeviceConflictException(String message) {
        super(message);
    }

    public DeviceConflictException(String message, Throwable thr) {
        super(message, thr);
    }
}
