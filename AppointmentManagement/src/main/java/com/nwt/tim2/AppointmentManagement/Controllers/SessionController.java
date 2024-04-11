package com.nwt.tim2.AppointmentManagement.Controllers;

import com.nwt.tim2.AppointmentManagement.Dtos.SessionDto;
import com.nwt.tim2.AppointmentManagement.Models.*;
import com.nwt.tim2.AppointmentManagement.Service.*;
import com.nwt.tim2.AppointmentManagement.Requests.*;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RefreshScope
@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSession(@RequestBody CreateSessionRequest request) {
            SessionDto session = sessionService.createSession(request.getPsychologistId(), request.getDay(), request.getTime());
            return ResponseEntity.ok(session);
    }

    @PostMapping("/addPatient")
    public ResponseEntity<?> addPatientToSession(@RequestBody AddPatientRequest request) {
            SessionDto session = sessionService.addPatientToSession(request.getPsychologistId(), request.getDay(), request.getTime(), request.getPatientId());
            return ResponseEntity.ok(session);
    }

    @GetMapping("/getAllAvailableSessions/{psychologistId}")
    public ResponseEntity<?> getAllAvailableSessions(@PathVariable String psychologistId) {
            List<SessionDto> sessions = sessionService.getAllAvailableSessions(psychologistId);
            return ResponseEntity.ok(sessions);
    }

    @DeleteMapping("/deleteSession/{psychologistId}/{day}/{time}")
    public ResponseEntity<?> deleteSessionByPsychologistIdAndDayAndTime(@RequestBody SessionDto deleteSessionRequest) {
            sessionService.deleteSessionByPsychologistIdAndDayAndTime(deleteSessionRequest);
            return ResponseEntity.ok().build();
    }

    @PostMapping("/removeUser/{userId}/{psychologistId}")
    public ResponseEntity<?> removeUserFromSession(@PathVariable String userId,
                                                   @PathVariable String psychologistId) {

            sessionService.removeUserFromSession(userId, psychologistId);
            return ResponseEntity.ok().build();

    }

    @GetMapping("/isUserScheduled/{userId}")
    public ResponseEntity<?> isUserAlreadyScheduled(@PathVariable String userId) {
            boolean scheduled = sessionService.isUserAlreadyScheduled(userId);
            return ResponseEntity.ok(scheduled);

    }

    @PutMapping("/updateSession/{psychologistId}/{userId}")
    public ResponseEntity<?> updateSession(@PathVariable String psychologistId,
                                           @PathVariable String userId,
                                           @RequestBody UpdateSessionRequest request) {

            SessionDto session = sessionService.updateSession(psychologistId, userId, request.getNewDay(), request.getNewTime());
            return ResponseEntity.ok(session);

    }

    @GetMapping("/getPsychologistSessions/{psychologistId}")
    public ResponseEntity<?> getPsychologistSessions(@PathVariable String psychologistId) {
            List<SessionDto> sessions = sessionService.getPsychologistSessions(psychologistId);
            return ResponseEntity.ok(sessions);

    }

    @GetMapping("/getUserSession/{userId}")
    public ResponseEntity<?> getUserSession(@PathVariable String userId) {
            SessionDto session = sessionService.getUserSession(userId);
            return ResponseEntity.ok(session);
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

