package com.nwt.tim2.AppointmentManagement.Controllers;

import com.nwt.tim2.AppointmentManagement.Dtos.PsychologistDto;
import com.nwt.tim2.AppointmentManagement.Models.Psychologist;
import com.nwt.tim2.AppointmentManagement.Responses.UserDTOResponse;
import com.nwt.tim2.AppointmentManagement.Service.PsychologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@RefreshScope
@RestController
@RequestMapping("/psychologists")
public class PsychologistController {
    @Autowired
   PsychologistService psychologistService;
    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/save")
    public ResponseEntity<?> savePsychologist(@RequestBody PsychologistDto psychologist) {

            PsychologistDto savedPsychologist = psychologistService.savePsychologist(psychologist);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPsychologist);

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPsychologists() {
            List<PsychologistDto> psychologists = psychologistService.getAllPsychologists();
            return ResponseEntity.ok(psychologists);
    }

    @GetMapping("/find/{psychologistId}")
    public ResponseEntity<?> getPsychologistById(@PathVariable String psychologistId) {
            System.out.println(psychologistId);
            PsychologistDto psychologist = psychologistService.getPsychologistById((psychologistId));
            return ResponseEntity.ok(psychologist);

    }
    @GetMapping("/findUserPsychologist/{psychologistId}")
    public ResponseEntity<?> getUserPsychologistById(@PathVariable String psychologistId) {
        PsychologistDto psychologist = psychologistService.getPsychologistById(psychologistId);
        String url = "http://userservice/userId/" + psychologistId;
        if (psychologist != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.ALL));
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{psychologistId}")
    public ResponseEntity<?> deletePsychologist(@PathVariable UUID psychologistId) {
            psychologistService.deletePsychologist(psychologistId.toString());
            return ResponseEntity.ok("Psychologist deleted successfully.");

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

