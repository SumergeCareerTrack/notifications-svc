package com.sumerge.careertrack.notifications_svc.exceptions;

public class DoesNotExistException extends NotificationsException {

    public static final String ENTITY_NAME = "Entity name 0\"%s\" does not exist.";
    public static final String ACTION = "Action \"%s\" does not exist.";


    public DoesNotExistException() {
        super();
    }

    public DoesNotExistException(String message) {
        super(message);
    }

    public DoesNotExistException(String message, Object... args) {
        super(String.format(message, args));
    }
}
