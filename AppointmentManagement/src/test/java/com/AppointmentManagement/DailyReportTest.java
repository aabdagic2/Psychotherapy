package com.AppointmentManagement;

import com.nwt.tim2.AppointmentManagement.Controllers.DailyReportController;
import com.nwt.tim2.AppointmentManagement.Dtos.DailyReportDto;
import com.nwt.tim2.AppointmentManagement.Models.DailyReport;
import com.nwt.tim2.AppointmentManagement.Service.DailyReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DailyReportTest {

    @Mock
    private DailyReportService dailyReportService;

    @InjectMocks
    private DailyReportController dailyReportController;

    private MockMvc mockMvc;

    @Test
    void testGetDailyReports() throws Exception {
        // Arrange
        String patientId = "patientId";
        List<DailyReportDto> dailyReports = Arrays.asList(new DailyReportDto(), new DailyReportDto());
        when(dailyReportService.getDailyReports(patientId)).thenReturn(dailyReports);
        mockMvc = MockMvcBuilders.standaloneSetup(dailyReportController).build();
        mockMvc.perform(get("/api/daily-reports")
                        .param("patientId", patientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(dailyReports.size()));
    }

    @Test
    void testCreateDailyReport() throws Exception {
        DailyReportDto dailyReportDto = new DailyReportDto();
        DailyReportDto createdDailyReportDto = new DailyReportDto();
        when(dailyReportService.createDailyReport(dailyReportDto)).thenReturn(createdDailyReportDto);

        mockMvc = MockMvcBuilders.standaloneSetup(dailyReportController).build();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/daily-reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dailyReportDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").exists());
    }

    @Test
    void testUpdateDailyReport() throws Exception {
        DailyReportDto dailyReportDto = new DailyReportDto();
        dailyReportDto.setDailyReportId("dailyReportId");
        DailyReportDto updatedDailyReportDto = new DailyReportDto();
        updatedDailyReportDto.setDailyReportId("dailyReportId");
        when(dailyReportService.updateDailyReport(dailyReportDto)).thenReturn(updatedDailyReportDto);
        mockMvc = MockMvcBuilders.standaloneSetup(dailyReportController).build();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/api/daily-reports/{dailyReportId}", "dailyReportId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dailyReportDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dailyReportId").value("dailyReportId"));
    }

    @Test
    void testDeleteDailyReport() throws Exception {
        String dailyReportId = "dailyReportId";
        mockMvc = MockMvcBuilders.standaloneSetup(dailyReportController).build();
        mockMvc.perform(delete("/api/daily-reports/{dailyReportId}", dailyReportId))
                .andExpect(status().isNoContent());

        verify(dailyReportService, times(1)).deleteDailyReport(dailyReportId);
    }
}
