package com.sumerge.careertrack.notifications_svc.services;



import com.sumerge.careertrack.notifications_svc.entities.Notification;
import com.sumerge.careertrack.notifications_svc.entities.enums.ActionEnum;
import com.sumerge.careertrack.notifications_svc.entities.enums.EntityTypeEnum;
import com.sumerge.careertrack.notifications_svc.entities.requests.NotificationRequestDTO;
import com.sumerge.careertrack.notifications_svc.entities.responses.NotificationResponseDTO;
import com.sumerge.careertrack.notifications_svc.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


@Service
@AllArgsConstructor
public class KafkaConsumerService {
    private final NotificationService notificationService;

    @KafkaListener(topics ="${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String messageJson) throws Exception {
        NotificationRequestDTO notificationRequest = parseJsonToDTO(messageJson);
        NotificationResponseDTO notification = notificationService.createNotification(notificationRequest);
        System.out.println("Saved: " + notification);
    }

    private NotificationRequestDTO parseJsonToDTO(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);

        NotificationRequestDTO notificationRequest = new NotificationRequestDTO();
        notificationRequest.setReceiverID(UUID.fromString(jsonObject.getString("receiverID")));
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