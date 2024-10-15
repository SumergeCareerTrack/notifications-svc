package com.sumerge.careertrack.notifications_svc.mappers;

import com.sumerge.careertrack.notifications_svc.entities.Notification;
import com.sumerge.careertrack.notifications_svc.entities.responses.NotificationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;



@Mapper(componentModel = "spring")
public interface NotificationMapper {
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


}
