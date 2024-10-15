package com.sumerge.careertrack.notifications_svc.entities;


import com.sumerge.careertrack.notifications_svc.entities.enums.ActionEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Actions {
    @Id
    private UUID id;

    private ActionEnum name;
}
