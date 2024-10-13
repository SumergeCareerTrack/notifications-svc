package com.sumerge.careertrack.notifications_svc.mappers;

import com.sumerge.careertrack.notifications_svc.entities.Actions;
import com.sumerge.careertrack.notifications_svc.entities.EntitiesType;
import com.sumerge.careertrack.notifications_svc.entities.Notification;
import com.sumerge.careertrack.notifications_svc.entities.NotificationData;
import com.sumerge.careertrack.notifications_svc.entities.enums.ActionEnum;
import com.sumerge.careertrack.notifications_svc.entities.enums.EntityTypeEnum;
import com.sumerge.careertrack.notifications_svc.entities.requests.NotificationRequestDTO;
import com.sumerge.careertrack.notifications_svc.entities.responses.NotificationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mappings({
            @Mapping(target = "notificationData", source = "notificationData"),
            @Mapping(target = "receiverID", source = "dto.receiverID"),
            @Mapping(target = "seen", source = "dto.seen")
    })
    Notification toNotification(NotificationRequestDTO dto, NotificationData notificationData);

    @Mappings({
            @Mapping(target = "actionId", expression = "java(mapAction(dto.getActionName()))"),
            @Mapping(target = "entityTypeName", expression = "java(mapEntityType(dto.getEntityTypeName()))"),
            @Mapping(target = "entityId", source = "dto.entityId"),
            @Mapping(target = "actorId", source = "dto.actorId"),
            @Mapping(target = "date", source = "dto.date")
    })
    NotificationData toNotificationData(NotificationRequestDTO dto);
    @Mappings({
        @Mapping(target = "id", source = "notification.notificationID"),
        @Mapping(target = "receiverID", source = "notification.receiverID"),
        @Mapping(target = "actorId", source = "notification.notificationData.actorId"),
        @Mapping(target = "name", source = "notification.notificationData.actionId.name"),
        @Mapping(target = "entityId", source = "notification.notificationData.entityId"),
        @Mapping(target = "entityTypeName", source = "notification.notificationData.entityTypeName.name"),
        @Mapping(target = "date", source = "notification.notificationData.date"),
        @Mapping(target = "seen", source = "notification.seen")
    })
    NotificationResponseDTO toNotificationResponse(Notification notification);

    default Actions mapAction(ActionEnum actionEnum) {
        if (actionEnum == null) {
            return null;
        }
        Actions action = new Actions();
        action.setId(UUID.randomUUID()); // You may want to modify this to fetch from the database instead
        action.setName(actionEnum);
        return action;
    }

    default EntitiesType mapEntityType(EntityTypeEnum entityTypeEnum) {
        if (entityTypeEnum == null) {
            return null;
        }
        EntitiesType entityType = new EntitiesType();
        entityType.setName(entityTypeEnum);
        return entityType;
    }
}
