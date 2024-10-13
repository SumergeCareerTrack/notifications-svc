package com.sumerge.careertrack.notifications_svc.serializers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.careertrack.notifications_svc.entities.Notification;
import org.apache.kafka.common.serialization.Serializer;

public class NotificationSerializer implements Serializer<Notification> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Notification notification) {
        try {
            return objectMapper.writeValueAsBytes(notification);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing Notification to JSON", e);
        }
    }
}
