//package com.AppointmentManagement;
//
//import com.nwt.tim2.AppointmentManagement.Controllers.PsychologistController;
//import com.nwt.tim2.AppointmentManagement.Dtos.PsychologistDto;
//import com.nwt.tim2.AppointmentManagement.Exception.GlobalRestExceptionHandler;
//import com.nwt.tim2.AppointmentManagement.Exception.UserNotFound;
//import com.nwt.tim2.AppointmentManagement.Exception.ValidationErrorResponse;
//import com.nwt.tim2.AppointmentManagement.Models.Psychologist;
//import com.nwt.tim2.AppointmentManagement.Service.PsychologistService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.mockito.Mockito.*;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//class PsychologistControllerTest {
//
//    private MockMvc mockMvc;
//    @Mock
//    private PsychologistService psychologistService;
//
//    @InjectMocks
//    private PsychologistController psychologistController;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(psychologistController).build();
//    }
//
//    @Test
//    void savePsychologist() throws Exception {
//        PsychologistDto psychologistDto = new PsychologistDto();
//        when(psychologistService.savePsychologist(any(PsychologistDto.class))).thenAnswer(invocation -> {
//            PsychologistDto savedPsychologist = invocation.getArgument(0);
//            savedPsychologist.setUserId(UUID.randomUUID().toString());
//            return savedPsychologist;
//        });
//
//        mockMvc.perform(post("/psychologists/save")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(psychologistDto)))
//                .andExpect(status().isCreated());
//
//        verify(psychologistService, times(1)).savePsychologist(any(PsychologistDto.class));
//    }
//
//    @Test
//    void getAllPsychologists() throws Exception {
//        List<PsychologistDto> psychologists = new ArrayList<>();
//        psychologists.add(new PsychologistDto());
//        psychologists.add(new PsychologistDto());
//
//        when(psychologistService.getAllPsychologists()).thenReturn(psychologists);
//
//        mockMvc.perform(get("/psychologists/all"))
//                .andExpect(status().isOk());
//
//        verify(psychologistService, times(1)).getAllPsychologists();
//    }
//
//    @Test
//    void getPsychologistById() throws Exception {
//        PsychologistDto psychologistDto = new PsychologistDto();
//        psychologistDto.setUserId(UUID.randomUUID().toString());
//
//        when(psychologistService.getPsychologistById(psychologistDto.getUserId())).thenReturn(psychologistDto);
//
//        mockMvc.perform(get("/psychologists/find/{psychologistId}", psychologistDto.getUserId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userId").value(psychologistDto.getUserId()));
//
//        verify(psychologistService, times(1)).getPsychologistById(psychologistDto.getUserId());
//    }
//
//    @Test
//    void deletePsychologist() throws Exception {
//        PsychologistDto psychologistDto = new PsychologistDto();
//        psychologistDto.setUserId(UUID.randomUUID().toString());
//
//        mockMvc.perform(delete("/psychologists/delete/{psychologistId}", psychologistDto.getUserId()))
//                .andExpect(status().isOk());
//
//        verify(psychologistService, times(1)).deletePsychologist(psychologistDto.getUserId());
//    }
//    @Test
//    void savePsychologist_InvalidInput_BadRequest() throws Exception {
//        PsychologistDto psychologistDto = new PsychologistDto();
//        doThrow(ValidationErrorResponse.class).when(psychologistService).savePsychologist(any(PsychologistDto.class));
//
//        mockMvc = MockMvcBuilders.standaloneSetup(psychologistController).build();
//
//        mockMvc.perform(post("/psychologists/save")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(psychologistDto)))
//                .andExpect(status().isBadRequest())
//                .andExpect(result -> assertInstanceOf(ValidationErrorResponse.class, result.getResolvedException()))
//                .andExpect(result -> assertEquals("Validation error occurred", result.getResolvedException().getMessage()));
//
//        verify(psychologistService, times(1)).savePsychologist(any(PsychologistDto.class));
//    }
//
//}
//
