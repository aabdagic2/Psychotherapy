package com.AppointmentManagement;

import com.nwt.tim2.AppointmentManagement.Controllers.PatientController;
import com.nwt.tim2.AppointmentManagement.Dtos.Mapper.PatientMapper;
import com.nwt.tim2.AppointmentManagement.Dtos.PatientDto;
import com.nwt.tim2.AppointmentManagement.Models.Patient;
import com.nwt.tim2.AppointmentManagement.Repos.PatientRepo;
import com.nwt.tim2.AppointmentManagement.Service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PatientController.class)
class PatientTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Mock
    private PatientRepo patientRepository;

    @Mock
    private PatientMapper patientMapper;



    @Test
    void savePatient() throws Exception {
        PatientDto patient = new PatientDto();
        when(patientService.savePatient(any())).thenReturn(patient);

        this.mockMvc.perform(post("/patients/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findById() throws Exception {
        Optional<PatientDto> patient = Optional.of(new PatientDto());
        when(patientService.findById(any())).thenReturn(patient);

        this.mockMvc.perform(get("/patients/find/patientId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findAllPatients() throws Exception {
        List<PatientDto> patients = new ArrayList<>();
        when(patientService.findAllPatients()).thenReturn(patients);

        this.mockMvc.perform(get("/patients/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deletePatient() throws Exception {
        this.mockMvc.perform(delete("/patients/delete/patientId"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Patient deleted successfully")));
    }

    @Test
    void checkIfPatientHasChosenPsychologist() throws Exception {
        when(patientService.checkIfPatientHasChosenPsychologist(any())).thenReturn(true);

        this.mockMvc.perform(get("/patients/checkIfPatientHasChosenPsychologist/patientId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAllPatientsThatGotNoReport() throws Exception {
        List<PatientDto> patients = new ArrayList<>();
        when(patientService.getAllPatientsThatGotNoReport(any())).thenReturn(patients);

        this.mockMvc.perform(get("/patients/getAllPatientsThatGotNoReport/psychologistId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
