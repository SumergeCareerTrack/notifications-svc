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
        JSONObject jsonObject = new JSONObject(messageJson);
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Date date = formatter.parse(jsonObject.getString("date"));
        try{
            NotificationRequestDTO notificationRequest = NotificationRequestDTO.builder()
                    .actionName(mapAction(jsonObject.getString("actionName").toLowerCase()))
                    .actorId(UUID.fromString(jsonObject.getString("actorId")))
                    .entityId(UUID.fromString(jsonObject.getString("entityId")))
                    .entityTypeName(mapEntityType(jsonObject.getString("entityTypeName").toLowerCase()))
                    .date(date)
                    .receiverID(IntStream.range(0, jsonObject.getJSONArray("receiverID").length())
                            .mapToObj(jsonObject.getJSONArray("receiverID")::getString)
                            .map(UUID::fromString)
                            .collect(Collectors.toList()))
                    .seen(jsonObject.getBoolean("seen"))
                    .build();
            NotificationResponseDTO notification = notificationService.createNotification(notificationRequest);
            System.out.println("Saved: " + notification);
        } catch (Exception e) {
            System.err.println("Error processing message: " + messageJson);
            System.err.println(e.getMessage());
            kafkaTemplate.send("DLT_Topic", messageJson);
            System.out.println("Sent message to DLT: " + messageJson);
        }

    }
public EntityTypeEnum mapEntityType(String entityTypeName) throws Exception {
    return switch (entityTypeName) {
        case "learning" -> EntityTypeEnum.LEARNING;
        case "blog" -> EntityTypeEnum.BLOG;
        case "wiki" -> EntityTypeEnum.WIKI;
        default -> throw new Exception("Does not Exist");
    };
    }

    public ActionEnum mapAction(String actionName) throws Exception {
        return switch (actionName) {
            case "approval" -> ActionEnum.APPROVAL;
            case "rejection" -> ActionEnum.REJECTION;
            case "submission" -> ActionEnum.SUBMISSION;
            default -> throw new Exception("Does not Exist");
        };
    }

}