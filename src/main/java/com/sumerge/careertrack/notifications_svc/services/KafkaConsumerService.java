package com.sumerge.careertrack.notifications_svc.services;



import com.sumerge.careertrack.notifications_svc.entities.Notification;
import com.sumerge.careertrack.notifications_svc.entities.enums.ActionEnum;
import com.sumerge.careertrack.notifications_svc.entities.enums.EntityTypeEnum;
import com.sumerge.careertrack.notifications_svc.entities.requests.NotificationRequestDTO;
import com.sumerge.careertrack.notifications_svc.entities.responses.NotificationResponseDTO;
import com.sumerge.careertrack.notifications_svc.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@AllArgsConstructor
public class KafkaConsumerService {
    private final KafkaTemplate<String, String> kafkaTemplate;


    private final NotificationService notificationService;


    @RetryableTopic(include = {Exception.class},
            attempts = "2",
            backoff = @Backoff(delay = 1000, multiplier = 2),
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            retryTopicSuffix = "-custom-try",
            dltTopicSuffix = "-dead-t")
    @KafkaListener(topics ="${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String messageJson) throws Exception {
        try{
            NotificationRequestDTO notificationRequest = parseJsonToDTO(messageJson);
            NotificationResponseDTO notification = notificationService.createNotification(notificationRequest);
            System.out.println("Saved: " + notification);
        } catch (Exception e) {
            System.err.println("Error processing message: " + messageJson);
            // Send the message to DLT
            kafkaTemplate.send("DLT_Topic", messageJson);
            System.out.println("Sent message to DLT: " + messageJson);
        }

    }

    private NotificationRequestDTO parseJsonToDTO(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray uuidStrings = jsonObject.getJSONArray("receiverID");
        System.out.println(uuidStrings);
        List<UUID> receivers = IntStream.range(0, uuidStrings.length())
                .mapToObj(uuidStrings::getString)    // Map each index to a UUID string
                .map(UUID::fromString)               // Convert each string to a UUID
                .toList();
        NotificationRequestDTO notificationRequest = new NotificationRequestDTO();
        notificationRequest.setReceiverID(receivers);
        notificationRequest.setActorId(UUID.fromString(jsonObject.getString("actorId")));
        notificationRequest.setEntityId(UUID.fromString(jsonObject.getString("entityId")));
        notificationRequest.setEntityTypeName(
                mapEntityType(jsonObject.getString("entityTypeName").toLowerCase()));
        notificationRequest.setActionName(
                mapAction(jsonObject.getString("actionName").toLowerCase()));
        String dateString = jsonObject.getString("date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = dateFormat.parse(dateString);
            notificationRequest.setDate(date);
        } catch (ParseException e) {
            System.err.println("Date parsing failed: " + e.getMessage());
            notificationRequest.setDate(null);
        }
        notificationRequest.setSeen(jsonObject.getBoolean("seen"));
        return notificationRequest;
    }

public EntityTypeEnum mapEntityType(String entityTypeName) {
    return switch (entityTypeName) {
        case "learning" -> EntityTypeEnum.LEARNING;
        case "blog" -> EntityTypeEnum.BLOG;
        case "wiki" -> EntityTypeEnum.WIKI;
        default -> EntityTypeEnum.NOTIFICATION;
    };
    }

    public ActionEnum mapAction(String actionName) {
        return switch (actionName) {
            case "approval" -> ActionEnum.APPROVAL;
            case "rejection" -> ActionEnum.REJECTION;
            default -> ActionEnum.SUBMISSION;
        };
    }

}