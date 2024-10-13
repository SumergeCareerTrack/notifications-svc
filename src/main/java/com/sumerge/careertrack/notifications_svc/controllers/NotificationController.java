package com.sumerge.careertrack.notifications_svc.controllers;

import com.sumerge.careertrack.notifications_svc.entities.requests.NotificationRequestDTO;
import com.sumerge.careertrack.notifications_svc.entities.responses.NotificationResponseDTO;
import com.sumerge.careertrack.notifications_svc.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;


    @GetMapping("/{receiverId}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationByReceiverId(@PathVariable String receiverId) {
        List<NotificationResponseDTO> response = notificationService.getNotificationByReceiverID(receiverId);
        return ResponseEntity.ok(response);
    }



}
