package com.sumerge.careertrack.notifications_svc.repositories;

import com.sumerge.careertrack.notifications_svc.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByReceiverID(UUID uuid);
}
