package com.sumerge.careertrack.notifications_svc.repositories;

import com.sumerge.careertrack.notifications_svc.entities.Actions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActionsRepository extends JpaRepository<Actions, UUID> {

}
