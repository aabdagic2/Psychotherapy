//package com.AppointmentManagement;
//
//import com.nwt.tim2.AppointmentManagement.Controllers.SessionController;
//import com.nwt.tim2.AppointmentManagement.Dtos.SessionDto;
//import com.nwt.tim2.AppointmentManagement.Models.Session;
//import com.nwt.tim2.AppointmentManagement.Requests.AddPatientRequest;
//import com.nwt.tim2.AppointmentManagement.Requests.*;
//import com.nwt.tim2.AppointmentManagement.Requests.CreateSessionRequest;
//import com.nwt.tim2.AppointmentManagement.Requests.DeleteSessionRequest;
//import com.nwt.tim2.AppointmentManagement.Requests.UpdateSessionRequest;
//import com.nwt.tim2.AppointmentManagement.Service.SessionService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//@ExtendWith(MockitoExtension.class)
//class SessionTest {
//
//    @Mock
//    private SessionService sessionService;
//
//    @InjectMocks
//    private SessionController sessionController;
//
//    @Test
//    void testCreateSession() {
//        CreateSessionRequest request = new CreateSessionRequest("psychologistId", "day", "time");
//        SessionDto sessionDto = new SessionDto();
//        when(sessionService.createSession(anyString(), anyString(), anyString())).thenReturn(sessionDto);
//
//        ResponseEntity<?> response = sessionController.createSession(request);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(sessionDto, response.getBody());
//        verify(sessionService, times(1)).createSession(anyString(), anyString(), anyString());
//    }
//    @Test
//    void testAddPatientToSession() {
//        AddPatientRequest request = new AddPatientRequest("psychologistId", "day", "time", "patientId");
//        SessionDto sessionDto = new SessionDto();
//        when(sessionService.addPatientToSession(anyString(), anyString(), anyString(), anyString())).thenReturn(sessionDto);
//
//        ResponseEntity<?> response = sessionController.addPatientToSession(request);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(sessionDto, response.getBody());
//        verify(sessionService, times(1)).addPatientToSession(anyString(), anyString(), anyString(), anyString());
//    }
//
//    @Test
//    void testGetAllAvailableSessions() {
//        String psychologistId = "psychologistId";
//        List<SessionDto> sessions = new ArrayList<>();
//        when(sessionService.getAllAvailableSessions(psychologistId)).thenReturn(sessions);
//
//        ResponseEntity<?> response = sessionController.getAllAvailableSessions(psychologistId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(sessions, response.getBody());
//        verify(sessionService, times(1)).getAllAvailableSessions(psychologistId);
//    }
//
////    @Test
////    void testDeleteSessionByPsychologistIdAndDayAndTime() {
////        SessionDto deleteSessionRequest = new SessionDto();
////        ResponseEntity<?> response = sessionController.deleteSessionByPsychologistIdAndDayAndTime(deleteSessionRequest);
////
////        assertEquals(HttpStatus.OK, response.getStatusCode());
////        verify(sessionService, times(1)).deleteSessionByPsychologistIdAndDayAndTime(deleteSessionRequest);
////    }
//
//    @Test
//    void testRemoveUserFromSession() {
//        String userId = "userId";
//        String psychologistId = "psychologistId";
//        ResponseEntity<?> response = sessionController.removeUserFromSession(userId, psychologistId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(sessionService, times(1)).removeUserFromSession(userId, psychologistId);
//    }
//
//    @Test
//    void testIsUserAlreadyScheduled() {
//        String userId = "userId";
//        boolean scheduled = true;
//        when(sessionService.isUserAlreadyScheduled(userId)).thenReturn(scheduled);
//
//        ResponseEntity<?> response = sessionController.isUserAlreadyScheduled(userId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(scheduled, response.getBody());
//        verify(sessionService, times(1)).isUserAlreadyScheduled(userId);
//    }
//
//    @Test
//    void testUpdateSession() {
//        UpdateSessionRequest request = new UpdateSessionRequest("newDay", "newTime");
//        String psychologistId = "psychologistId";
//        String userId = "userId";
//        SessionDto sessionDto = new SessionDto();
//        when(sessionService.updateSession(anyString(), anyString(), anyString(), anyString())).thenReturn(sessionDto);
//
//        ResponseEntity<?> response = sessionController.updateSession(psychologistId, userId, request);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(sessionDto, response.getBody());
//        verify(sessionService, times(1)).updateSession(anyString(), anyString(), anyString(), anyString());
//    }
//
//    @Test
//    void testGetPsychologistSessions() {
//        String psychologistId = "psychologistId";
//        List<SessionDto> sessions = new ArrayList<>();
//        when(sessionService.getPsychologistSessions(psychologistId)).thenReturn(sessions);
//
//        ResponseEntity<?> response = sessionController.getPsychologistSessions(psychologistId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(sessions, response.getBody());
//        verify(sessionService, times(1)).getPsychologistSessions(psychologistId);
//    }
//
//    @Test
//    void testGetUserSession() {
//        String userId = "userId";
//        SessionDto sessionDto = new SessionDto();
//        when(sessionService.getUserSession(userId)).thenReturn(sessionDto);
//
//        ResponseEntity<?> response = sessionController.getUserSession(userId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(sessionDto, response.getBody());
//        verify(sessionService, times(1)).getUserSession(userId);
//    }
//}
