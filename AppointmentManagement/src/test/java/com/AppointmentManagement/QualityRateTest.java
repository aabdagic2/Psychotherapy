//package com.AppointmentManagement;
//
//import com.nwt.tim2.AppointmentManagement.Controllers.QualityRateController;
//import com.nwt.tim2.AppointmentManagement.Dtos.*;
//import com.nwt.tim2.AppointmentManagement.Models.Psychologist;
//import com.nwt.tim2.AppointmentManagement.Models.QualityRate;
//import com.nwt.tim2.AppointmentManagement.Service.QualityRateService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//@ExtendWith(MockitoExtension.class)
//class QualityRateTest {
//
//    @Mock
//    private QualityRateService qualityRateService;
//
//    @InjectMocks
//    private QualityRateController qualityRateController;
//
//    @Test
//    void testGetQualityRateForPsychologist() {
//        String psychologistId = "psychologistId";
//        Double qualityRate = 4.5;
//        when(qualityRateService.getQualityRateForPsychologist(psychologistId)).thenReturn(qualityRate);
//
//        ResponseEntity<?> response = qualityRateController.getQualityRateForPsychologist(psychologistId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(qualityRate, response.getBody());
//        verify(qualityRateService, times(1)).getQualityRateForPsychologist(psychologistId);
//    }
//
//    @Test
//    void testDidUserGiveRatingAfterLastSession() {
//        String userId = "userId";
//        boolean rated = true;
//        when(qualityRateService.didUserGiveRatingAfterLastSession(userId)).thenReturn(rated);
//
//        ResponseEntity<?> response = qualityRateController.didUserGiveRatingAfterLastSession(userId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(rated, response.getBody());
//        verify(qualityRateService, times(1)).didUserGiveRatingAfterLastSession(userId);
//    }
//
//    @Test
//    void testCreateQualityRate() {
//        QualityRateDto qualityRateDto = new QualityRateDto();
//        QualityRate qualityRate = new QualityRate();
//        when(qualityRateService.createQualityRate(qualityRateDto)).thenReturn(qualityRate);
//
//        ResponseEntity<?> response = qualityRateController.createQualityRate(qualityRateDto);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(qualityRate, response.getBody());
//        verify(qualityRateService, times(1)).createQualityRate(qualityRateDto);
//    }
//
//    @Test
//    void testGetQualityRateForPsychologistService() {
//        String psychologistId = "psychologistId";
//        Double qualityRate = 4.5;
//        Psychologist psychologist = new Psychologist();
//        when(qualityRateService.getQualityRateForPsychologist(psychologistId)).thenReturn(qualityRate);
//        when(qualityRateService.getQualityRateForPsychologist(psychologistId)).thenReturn(qualityRate);
//
//        Double result = qualityRateService.getQualityRateForPsychologist(psychologistId);
//
//        assertEquals(qualityRate, result);
//        verify(qualityRateService, times(1)).getQualityRateForPsychologist(psychologistId);
//    }
//
//    @Test
//    void testDidUserGiveRatingAfterLastSessionService() {
//        String userId = "userId";
//        LocalDateTime currentTime = LocalDateTime.now();
//        SessionDto session = new SessionDto();
//        session.setTime("12:00:00");
//        session.setDay("Monday");
//        when(qualityRateService.didUserGiveRatingAfterLastSession(userId)).thenReturn(true);
//
//        boolean result = qualityRateService.didUserGiveRatingAfterLastSession(userId);
//
//        assertTrue(result);
//        verify(qualityRateService, times(1)).didUserGiveRatingAfterLastSession(userId);
//    }
//
//    @Test
//    void testCreateQualityRateService() {
//        QualityRateDto qualityRateDto = new QualityRateDto();
//        qualityRateDto.setPatientId("patientId");
//        qualityRateDto.setPsychologistId("psychologistId");
//        QualityRate qualityRate = new QualityRate();
//        when(qualityRateService.createQualityRate(qualityRateDto)).thenReturn(qualityRate);
//
//        QualityRate result = qualityRateService.createQualityRate(qualityRateDto);
//
//        assertNotNull(result);
//        verify(qualityRateService, times(1)).createQualityRate(qualityRateDto);
//    }
//}
