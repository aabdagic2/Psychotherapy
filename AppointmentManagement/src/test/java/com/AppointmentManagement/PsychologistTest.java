package com.AppointmentManagement;

import com.nwt.tim2.AppointmentManagement.Controllers.PsychologistController;
import com.nwt.tim2.AppointmentManagement.Dtos.PsychologistDto;
import com.nwt.tim2.AppointmentManagement.Exception.UserNotFound;
import com.nwt.tim2.AppointmentManagement.Models.Psychologist;
import com.nwt.tim2.AppointmentManagement.Service.PsychologistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PsychologistTest {

    @Mock
    private PsychologistService psychologistService;

    @InjectMocks
    private PsychologistController psychologistController;

    @Test
    void testSavePsychologist() {
        PsychologistDto psychologistDto = new PsychologistDto();
        when(psychologistService.savePsychologist(any(PsychologistDto.class))).thenReturn(psychologistDto);

        ResponseEntity<?> response = psychologistController.savePsychologist(psychologistDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(psychologistDto, response.getBody());
        verify(psychologistService, times(1)).savePsychologist(any(PsychologistDto.class));
    }

    @Test
    void testGetAllPsychologists() {
        List<PsychologistDto> psychologists = List.of(new PsychologistDto(), new PsychologistDto());
        when(psychologistService.getAllPsychologists()).thenReturn(psychologists);

        ResponseEntity<?> response = psychologistController.getAllPsychologists();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(psychologists, response.getBody());
        verify(psychologistService, times(1)).getAllPsychologists();
    }

    @Test
    void testGetPsychologistById() {
        UUID id = UUID.randomUUID();
        PsychologistDto psychologistDto = new PsychologistDto();
        when(psychologistService.getPsychologistById(id.toString())).thenReturn((psychologistDto));

        ResponseEntity<?> response = psychologistController.getPsychologistById(id.toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(psychologistDto, response.getBody());
        verify(psychologistService, times(1)).getPsychologistById(id.toString());
    }





    @Test
    void testDeletePsychologist() {
        UUID id = UUID.randomUUID();

        ResponseEntity<?> response = psychologistController.deletePsychologist(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(psychologistService, times(1)).deletePsychologist(id);
    }
}
