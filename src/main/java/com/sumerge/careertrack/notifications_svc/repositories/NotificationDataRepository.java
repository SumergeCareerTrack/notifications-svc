package com.sumerge.careertrack.notifications_svc.repositories;

import com.sumerge.careertrack.notifications_svc.entities.NotificationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationDataRepository extends JpaRepository<NotificationData, UUID> {
}
