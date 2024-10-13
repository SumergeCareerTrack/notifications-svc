package com.sumerge.careertrack.notifications_svc.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationData {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false)
    private Actions actionId;
    @ManyToOne
    @JoinColumn(name = "entity_type_name", nullable = false)
    private EntitiesType entityTypeName;

    private UUID entityId;

    private UUID actorId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
