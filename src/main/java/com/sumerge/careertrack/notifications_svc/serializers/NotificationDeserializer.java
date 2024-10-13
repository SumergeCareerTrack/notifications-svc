package com.sumerge.careertrack.notifications_svc.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.careertrack.notifications_svc.entities.Notification;
import org.apache.kafka.common.serialization.Deserializer;
import java.util.Map;

public class NotificationDeserializer implements Deserializer<Notification> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Notification deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Notification.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing Notification from JSON", e);
        }
    }
}
