package com.sumerge.careertrack.notifications_svc.entities;


import com.sumerge.careertrack.notifications_svc.entities.enums.EntityTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntitiesType {
    @Id
    private EntityTypeEnum name;
}
