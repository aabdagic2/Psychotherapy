package com.nwt.tim2.AppointmentManagement.Controllers;

import com.nwt.tim2.AppointmentManagement.Dtos.QualityRateDto;
import com.nwt.tim2.AppointmentManagement.Models.QualityRate;
import com.nwt.tim2.AppointmentManagement.Service.QualityRateService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.time.LocalDateTime;
import java.util.*;
@RefreshScope
@RestController
@RequestMapping("/quality-rates")
public class QualityRateController {

    private final QualityRateService qualityRateService;

    public QualityRateController(QualityRateService qualityRateService) {
        this.qualityRateService = qualityRateService;
    }

    @GetMapping("/psychologist/{psychologistId}")
    public ResponseEntity<?> getQualityRateForPsychologist(@PathVariable String psychologistId) {
            Double qualityRate = qualityRateService.getQualityRateForPsychologist(psychologistId);
            return ResponseEntity.ok(qualityRate);

    }

    @GetMapping("/user-rating/{userId}")
    public ResponseEntity<?> didUserGiveRatingAfterLastSession(@PathVariable String userId) {
            boolean rated = qualityRateService.didUserGiveRatingAfterLastSession(userId);
            return ResponseEntity.ok(rated);

    }

    @PostMapping("/create/{patientId}/{psychologistId}/{rate}")
    public ResponseEntity<?> createQualityRate(@RequestBody QualityRateDto qualityRateDto) {
            QualityRate qualityRate = qualityRateService.createQualityRate(qualityRateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(qualityRate);
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

