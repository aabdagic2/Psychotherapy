package com.nwt.tim2.AppointmentManagement.Controllers;

import com.nwt.tim2.AppointmentManagement.Dtos.Mapper.PatientMapper;
import com.nwt.tim2.AppointmentManagement.Dtos.PatientDto;
import com.nwt.tim2.AppointmentManagement.Models.Patient;
import com.nwt.tim2.AppointmentManagement.Responses.UserDTOResponse;
import com.nwt.tim2.AppointmentManagement.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
   private final PatientMapper patientMapper;
    @Autowired
    public PatientController(PatientService patientService,PatientMapper patientMapper) {
        this.patientService = patientService;
        this.patientMapper=patientMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePatient(@RequestBody PatientDto patient) {

            PatientDto savedPatient = patientService.savePatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);

    }

    @GetMapping("/find/{patientId}")
    public ResponseEntity<?> findById(@PathVariable String patientId) {
            Optional<PatientDto> patient = patientService.findById(patientId);
            return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllPatients() {
            List<PatientDto> patients = patientService.findAllPatients();
            return ResponseEntity.ok(patients);
    }

    @DeleteMapping("/delete/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable String patientId) {
            patientService.deletePatient(patientId);
            return ResponseEntity.ok("Patient deleted successfully.");

    }

    @GetMapping("/checkIfPatientHasChosenPsychologist/{patientId}")
    public ResponseEntity<?> checkIfPatientHasChosenPsychologist(@PathVariable String patientId) {
            boolean hasChosenPsychologist = patientService.checkIfPatientHasChosenPsychologist(patientId);
            return ResponseEntity.ok(hasChosenPsychologist);
    }
    @GetMapping("/getAllPatientsThatGotNoReport/{psychologistId}")
    public ResponseEntity<?> getAllPatientsThatGotNoReport(@PathVariable String psychologistId) {
            List<PatientDto> patientsWithoutReport = patientService.getAllPatientsThatGotNoReport(psychologistId);
            return ResponseEntity.ok(patientsWithoutReport);

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

