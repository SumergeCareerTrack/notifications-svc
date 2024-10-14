package com.sumerge.careertrack.notifications_svc.exceptions;

public class AlreadyExistsException extends NotificationsException {

    public static final String NOTIFICATION = "Notification with actorId \"%s\" , Action Name \"%s\" and entity ID \"%s\" already exists.";

    public AlreadyExistsException() {
        super();
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Object... args) {
        super(String.format(message, args));
    }
}