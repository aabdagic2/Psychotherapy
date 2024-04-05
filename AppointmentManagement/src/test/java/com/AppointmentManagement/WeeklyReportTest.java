package com.AppointmentManagement;

import com.nwt.tim2.AppointmentManagement.Controllers.WeeklyReportController;
import com.nwt.tim2.AppointmentManagement.Dtos.WeeklyReportDto;
import com.nwt.tim2.AppointmentManagement.Models.WeeklyReport;
import com.nwt.tim2.AppointmentManagement.Service.WeeklyReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class WeeklyReportTest {

    @Mock
    private WeeklyReportService weeklyReportService;

    @InjectMocks
    private WeeklyReportController weeklyReportController;

    @Test
    void testCreateWeeklyReportForUser() {
        WeeklyReportDto weeklyReportDto = new WeeklyReportDto();
        when(weeklyReportService.createWeeklyReportForUser(any(WeeklyReportDto.class))).thenReturn(weeklyReportDto);

        ResponseEntity<?> response = weeklyReportController.createWeeklyReportForUser(weeklyReportDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(weeklyReportDto, response.getBody());
        verify(weeklyReportService, times(1)).createWeeklyReportForUser(any(WeeklyReportDto.class));
    }

    @Test
    void testDeleteWeeklyReportForUser() {
        String weeklyReportId = "weeklyReportId";
        ResponseEntity<?> response = weeklyReportController.deleteWeeklyReportForUser(weeklyReportId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(weeklyReportService, times(1)).deleteWeeklyReportForUser(weeklyReportId);
    }

    @Test
    void testGetWeeklyReportForUser() {
        String weeklyReportId = "weeklyReportId";
        WeeklyReportDto weeklyReportDto = new WeeklyReportDto();
        when(weeklyReportService.getWeeklyReportForUser(weeklyReportId)).thenReturn(weeklyReportDto);

        ResponseEntity<?> response = weeklyReportController.getWeeklyReportForUser(weeklyReportId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(weeklyReportDto, response.getBody());
        verify(weeklyReportService, times(1)).getWeeklyReportForUser(weeklyReportId);
    }

    @Test
    void testGetAllWeeklyReportsForPsychologist() {
        String psychologistId = "psychologistId";
        List<WeeklyReportDto> weeklyReports = new ArrayList<>();
        weeklyReports.add(new WeeklyReportDto());
        weeklyReports.add(new WeeklyReportDto());
        when(weeklyReportService.getAllWeeklyReportsForPsychologist(psychologistId)).thenReturn(weeklyReports);

        ResponseEntity<?> response = weeklyReportController.getAllWeeklyReportsForPsychologist(psychologistId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(weeklyReports, response.getBody());
        verify(weeklyReportService, times(1)).getAllWeeklyReportsForPsychologist(psychologistId);
    }
}
