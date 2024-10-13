package com.sumerge.careertrack.notifications_svc.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Notification",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"notificationData", "receiverID"})})
public class Notification  {

    @Id
    @GeneratedValue
    private UUID notificationID;

    @ManyToOne
    @JoinColumn(name = "notificationData", nullable = false)
    private NotificationData notificationData;

    @ElementCollection
    private List<UUID> receiverID;

    private boolean seen;
}
