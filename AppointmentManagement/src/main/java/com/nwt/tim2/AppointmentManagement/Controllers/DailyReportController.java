package com.nwt.tim2.AppointmentManagement.Controllers;

import com.nwt.tim2.AppointmentManagement.Dtos.DailyReportDto;
import com.nwt.tim2.AppointmentManagement.Dtos.Mapper.DailyReportMapper;
import com.nwt.tim2.AppointmentManagement.Models.*;
import com.nwt.tim2.AppointmentManagement.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/daily-reports")
public class DailyReportController {

    private final DailyReportService dailyReportService;

    @Autowired
    public DailyReportController(DailyReportService dailyReportService, DailyReportMapper dailyReportMapper) {
        this.dailyReportService = dailyReportService;
    }

    @GetMapping
    public ResponseEntity<?> getDailyReports(
           @Valid @RequestParam String patientId
    ) {
            List<DailyReportDto> dailyReports = dailyReportService.getDailyReports(patientId);
            return ResponseEntity.ok(dailyReports);

    }

    @PostMapping
    public ResponseEntity<?> createDailyReport(
          @Valid  @RequestBody DailyReportDto dailyReportDto
    ) {
            DailyReportDto dailyReport = dailyReportService.createDailyReport(dailyReportDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(dailyReport);

    }
    @PutMapping("/{dailyReportId}")
    public ResponseEntity<?> updateDailyReport(
           @Valid @RequestBody DailyReportDto dailyReportDTO
    ) {
            DailyReportDto updatedDailyReport = dailyReportService.updateDailyReport(dailyReportDTO);
            return ResponseEntity.ok(updatedDailyReport);

    }

    @DeleteMapping("/{dailyReportId}")
    public ResponseEntity<?> deleteDailyReport(@Valid @PathVariable String dailyReportId) {
            dailyReportService.deleteDailyReport(dailyReportId);
            return ResponseEntity.noContent().build();

    }

}
