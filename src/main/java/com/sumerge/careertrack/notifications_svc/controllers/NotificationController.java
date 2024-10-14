package com.sumerge.careertrack.notifications_svc.controllers;

import com.sumerge.careertrack.notifications_svc.entities.requests.NotificationRequestDTO;
import com.sumerge.careertrack.notifications_svc.entities.responses.NotificationResponseDTO;
import com.sumerge.careertrack.notifications_svc.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;


    @GetMapping("/{receiverId}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationByReceiverId(
            @PathVariable String receiverId,
            @RequestParam(required = false) Boolean filterBySeen) {
        List<NotificationResponseDTO> response = notificationService.getNotificationByReceiverID(receiverId);
        if (filterBySeen != null) {
            response = response.stream()
                    .filter(notification -> notification.isSeen() == filterBySeen)
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(response);
    }


}
