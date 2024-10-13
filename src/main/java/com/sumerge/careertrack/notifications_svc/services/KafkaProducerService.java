package com.sumerge.careertrack.notifications_svc.services;

import com.sumerge.careertrack.notifications_svc.entities.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Notification> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, Notification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, Notification message)
    {
        kafkaTemplate.send(topic, message);
    }
}