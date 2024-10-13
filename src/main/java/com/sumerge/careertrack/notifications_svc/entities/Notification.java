package com.sumerge.careertrack.notifications_svc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Builder
@IdClass(NotificationId.class)
@AllArgsConstructor
@NoArgsConstructor

public class Notification implements Serializable {
    @Id
    private String notificationData;
    @Id
    private UUID receiverID;
    private boolean seen;
}
