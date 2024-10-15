package com.sumerge.careertrack.notifications_svc.repositories;

import com.sumerge.careertrack.notifications_svc.entities.EntitiesType;
import com.sumerge.careertrack.notifications_svc.entities.enums.EntityTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface EntitiesTypeRepository extends JpaRepository<EntitiesType, UUID> {

    EntitiesType findByName(EntityTypeEnum s);
}
