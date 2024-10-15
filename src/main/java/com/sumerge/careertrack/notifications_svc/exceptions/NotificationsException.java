package com.sumerge.careertrack.notifications_svc.exceptions;

public class NotificationsException extends RuntimeException {
    public NotificationsException() {
        super();
    }

    public NotificationsException(String message) {
        super(message);
    }

    public NotificationsException(String message, Object... args) {
        super(String.format(message, args));
    }
}