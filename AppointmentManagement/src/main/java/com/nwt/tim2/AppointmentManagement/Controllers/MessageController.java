package com.nwt.tim2.AppointmentManagement.Controllers;

import com.nwt.tim2.AppointmentManagement.Dtos.MessageDto;
import com.nwt.tim2.AppointmentManagement.Models.Message;
import com.nwt.tim2.AppointmentManagement.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RefreshScope
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getAllMessagesForPatientOrPsychologist(@PathVariable String patientId) {
            List<MessageDto> messages = messageService.getAllMessagesForPatientOrPsychologist(patientId);
            return ResponseEntity.ok(messages);

    }

    @PostMapping("/create/{patientId}")
    public ResponseEntity<?> createMessage(@RequestBody MessageDto message, @PathVariable String patientId) {
            MessageDto createdMessage = messageService.createMessage(message, patientId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMessage(@RequestBody MessageDto message) {
            boolean deleted = messageService.deleteMessage(message);
                return ResponseEntity.ok("Message deleted successfully.");


    }

    private Map<String, Object> createErrorResponse(String status, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status);
        errorResponse.put("errors", createErrorDetails(message));
        return errorResponse;
    }

    private Map<String, Object> createErrorDetails(String message) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", message);
        errorDetails.put("details", message);
        return errorDetails;
    }
}