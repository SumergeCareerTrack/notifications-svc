package com.sumerge.careertrack.notifications_svc.services;

import com.sumerge.careertrack.notifications_svc.entities.Actions;
import com.sumerge.careertrack.notifications_svc.entities.EntitiesType;
import com.sumerge.careertrack.notifications_svc.entities.Notification;
import com.sumerge.careertrack.notifications_svc.entities.NotificationData;
import com.sumerge.careertrack.notifications_svc.entities.enums.ActionEnum;
import com.sumerge.careertrack.notifications_svc.entities.enums.EntityTypeEnum;
import com.sumerge.careertrack.notifications_svc.entities.requests.NotificationRequestDTO;
import com.sumerge.careertrack.notifications_svc.entities.responses.NotificationResponseDTO;
import com.sumerge.careertrack.notifications_svc.mappers.NotificationMapper;
import com.sumerge.careertrack.notifications_svc.repositories.ActionsRepository;
import com.sumerge.careertrack.notifications_svc.repositories.EntitiesTypeRepository;
import com.sumerge.careertrack.notifications_svc.repositories.NotificationDataRepository;
import com.sumerge.careertrack.notifications_svc.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationDataRepository notificationDataRepository;
    private final ActionsRepository actionsRepository;
    private final EntitiesTypeRepository entitiesTypeRepository;
    private final NotificationMapper notificationMapper;


    public NotificationResponseDTO createNotification(NotificationRequestDTO notificationRequestDTO) throws Exception {
        Actions action = actionsRepository.findByName(ActionEnum.valueOf(String.valueOf(notificationRequestDTO.getActionName())));
        EntitiesType entitiesType = entitiesTypeRepository.findByName(EntityTypeEnum.valueOf(String.valueOf(notificationRequestDTO.getEntityTypeName())));

        NotificationData data = NotificationData.builder()
                .actionId(action)
                .entityTypeName(entitiesType)
                .entityId(notificationRequestDTO.getEntityId())
                .actorId(notificationRequestDTO.getActorId())
                .date(notificationRequestDTO.getDate())
                .build();

        if (notificationDataRepository.existsByEntityIdAndActorIdAndActionId(
                notificationRequestDTO.getEntityId(),
                notificationRequestDTO.getActorId(),
                action
        )) {
            System.out.println("Already Exists");
            return null;
        }

        NotificationData savedData = notificationDataRepository.save(data);

        Notification notification = new Notification();
        notification.setNotificationData(savedData);
        notification.setSeen(notificationRequestDTO.isSeen());
        //TODO RECIEVER ID SHOULD BE LIST
        notification.setReceiverID(notificationRequestDTO.getReceiverID());
        Notification savedNotification = notificationRepository.save(notification);

        return NotificationResponseDTO.builder()
                .id(savedNotification.getNotificationID()) // Assuming Notification has a getId() method
                .entityId(savedData.getEntityId())
                .actorId(savedData.getActorId())
                .name(action.getName())
                .entityTypeName(entitiesType.getName()) // Assuming EntitiesType has a getName() method
                .date(savedData.getDate())
                .build();
    }

    public List<NotificationResponseDTO> getNotificationByReceiverID(String receiverID) {
        List<Notification> notifications=notificationRepository.findByReceiverID(UUID.fromString(receiverID));
        return notifications.stream().map(notificationMapper::toNotificationResponse).toList();
    }


}
