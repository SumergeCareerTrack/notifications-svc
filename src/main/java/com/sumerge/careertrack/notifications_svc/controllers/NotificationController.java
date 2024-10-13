package com.sumerge.careertrack.notifications_svc.controllers;


import com.sumerge.careertrack.notifications_svc.entities.Notification;
import com.sumerge.careertrack.notifications_svc.services.KafkaProducerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
public class NotificationController {

    private final KafkaProducerService producerService;


    @PostMapping("/publish/notification")
    public ResponseEntity<String> publishMessage(@RequestBody Notification message) {
        System.out.println("Message received: " + message);
        producerService.sendMessage("notification", message);
        return ResponseEntity.ok("Message published to Kafka topic");
    }
}