package org.mabartos.api.service.invitations;

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
