package com.sumerge.careertrack.notifications_svc.entities.requests;

import com.sumerge.careertrack.notifications_svc.entities.enums.ActionEnum;
import com.sumerge.careertrack.notifications_svc.entities.enums.EntityTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {

    private UUID receiverID;
    private UUID actorId;
    private ActionEnum actionName;
    private UUID entityId;
    private EntityTypeEnum entityTypeName;
    private Date date;
    private boolean seen;

}
