package com.sumerge.careertrack.notifications_svc.entities;

import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
public class NotificationId implements Serializable {
    private String notificationData;
    private UUID receiverID;

}
