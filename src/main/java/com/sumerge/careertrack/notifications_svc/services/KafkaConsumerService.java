package com.sumerge.careertrack.notifications_svc.services;



import com.sumerge.careertrack.notifications_svc.entities.enums.ActionEnum;
import com.sumerge.careertrack.notifications_svc.entities.enums.EntityTypeEnum;
import com.sumerge.careertrack.notifications_svc.entities.requests.NotificationRequestDTO;
import com.sumerge.careertrack.notifications_svc.entities.responses.NotificationResponseDTO;
import com.sumerge.careertrack.notifications_svc.exceptions.AlreadyExistsException;
import com.sumerge.careertrack.notifications_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.notifications_svc.exceptions.NotificationsException;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
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

    private final NotificationService notificationService;


    @RetryableTopic(include = {NotificationsException.class,ParseException.class},
            attempts = "1")
    @KafkaListener(topics ="${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")

    public void listen(String messageJson) throws DoesNotExistException, AlreadyExistsException, ParseException {
        JSONObject jsonObject = new JSONObject(messageJson);
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Date date = formatter.parse(jsonObject.getString("date"));
        NotificationRequestDTO notificationRequest = toNotificationRequestDTo(jsonObject,date);
        List<NotificationResponseDTO> notification = notificationService.createNotification(notificationRequest);
        System.out.println("Saved: " + notification);
    }
    private NotificationRequestDTO toNotificationRequestDTo(JSONObject jsonObject,Date date) throws DoesNotExistException {
        return NotificationRequestDTO.builder()
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
    }

    public EntityTypeEnum mapEntityType(String entityTypeName) throws DoesNotExistException {
        return switch (entityTypeName) {
            case "learning" -> EntityTypeEnum.LEARNING;
            case "blog" -> EntityTypeEnum.BLOG;
            case "wiki" -> EntityTypeEnum.WIKI;
            case "career_package"-> EntityTypeEnum.CAREER_PACKAGE;
            default -> throw new DoesNotExistException(DoesNotExistException.ENTITY_NAME,entityTypeName);

        };
        }

    public ActionEnum mapAction(String actionName) throws DoesNotExistException {
        return switch (actionName) {
            case "approval" -> ActionEnum.APPROVAL;
            case "rejection" -> ActionEnum.REJECTION;
            case "submission" -> ActionEnum.SUBMISSION;
            default -> throw new DoesNotExistException(DoesNotExistException.ACTION,actionName);
        };
    }

}