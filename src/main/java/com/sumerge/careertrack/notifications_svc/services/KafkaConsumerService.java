package com.sumerge.careertrack.notifications_svc.services;


import com.sumerge.careertrack.notifications_svc.entities.Notification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "notification", groupId = "notification-group")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
    @KafkaListener(
            topics = "notification",
            containerFactory = "notificationKafkaListenerContainerFactory")
    public void greetingListener(Notification notification) {
        System.out.println("Notification received:");
        System.out.println("Data: " + notification.getNotificationData());
        System.out.println("Receiver ID: " + notification.getReceiverID());
    }
}