/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.service.home;

public class HomeInvitationConflictException extends RuntimeException {

    public HomeInvitationConflictException() {
        super();
    }

    public HomeInvitationConflictException(String message) {
        super(message);
    }

    public HomeInvitationConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
