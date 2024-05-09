package com.nwt.tim2.AppointmentManagement.Service;

import ba.unsa.etf.pnwt.proto.LoggingRequest;
import com.nwt.tim2.AppointmentManagement.Dtos.DailyReportDto;
import com.nwt.tim2.AppointmentManagement.Dtos.Mapper.DailyReportMapper;
import com.nwt.tim2.AppointmentManagement.Exception.DailyReportNotFound;
import com.nwt.tim2.AppointmentManagement.Exception.UserExists;
import com.nwt.tim2.AppointmentManagement.Exception.UserNotFound;
import com.nwt.tim2.AppointmentManagement.Exception.WeeklyReportNotFound;
import com.nwt.tim2.AppointmentManagement.Models.*;
import com.nwt.tim2.AppointmentManagement.Repos.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DailyReportService {

    private final DailyReportRepo dailyReportRepo;
    private final PatientRepo patientRepo;
    private final WeeklyReportRepo weeklyReportRepo;
    private final DailyReportMapper dailyReportMapper;
    @GrpcClient("logging")
    ba.unsa.etf.pnwt.proto.LoggingServiceGrpc.LoggingServiceBlockingStub loggingServiceBlockingStub;
    @Autowired
    public DailyReportService(DailyReportRepo dailyReportRepo, PatientRepo patientRepo, WeeklyReportRepo weeklyReportRepo,DailyReportMapper dailyReportMapper) {
        this.dailyReportRepo = dailyReportRepo;
        this.patientRepo = patientRepo;
        this.weeklyReportRepo=weeklyReportRepo;
        this.dailyReportMapper=dailyReportMapper;
    }

    public List<DailyReportDto> getDailyReports(String patientId) {
        Optional<Patient> patientOptional = patientRepo.findById(patientId);

        if (patientOptional.isEmpty()) {
            LoggingRequest loggingRequest = LoggingRequest.newBuilder()
                    .setServiceName("DailyReportService")
                    .setControllerName("DailyReportController")
                    .setActionUrl("/appointmentmanagement/getDailyReports")
                    .setActionType("GET")
                    .setActionResponse("ERROR")
                    .build();
            loggingServiceBlockingStub.logRequest(loggingRequest);
            throw new UserNotFound("Patient not found.");
        }

        LocalDate weekAgo = LocalDate.now().minusDays(7);
        List<DailyReport> d= dailyReportRepo.findByPatientAndCreatedAtAfter(patientOptional.get(), weekAgo);
        LoggingRequest loggingRequest = LoggingRequest.newBuilder()
                .setServiceName("DailyReportService")
                .setControllerName("DailyReportController")
                .setActionUrl("/appointmentmanagement/getDailyReports")
                .setActionType("GET")
                .setActionResponse("SUCCESS")
                .build();
        loggingServiceBlockingStub.logRequest(loggingRequest);
        return d.stream()
                .map(dailyReportMapper::toDto)
                .collect(Collectors.toList());
    }

    public DailyReportDto createDailyReport(@Valid DailyReportDto dailyReportDto) {
        DailyReport d=dailyReportMapper.fromDto(dailyReportDto);
        Optional<Patient> patientOptional = patientRepo.findById(d.getPatient().getUserId());
        Optional<WeeklyReport> weeklyReportOptional = weeklyReportRepo.findById(d.getWeeklyReport().getWeeklyReportId());
        if (patientOptional.isEmpty()) {
            LoggingRequest loggingRequest = LoggingRequest.newBuilder()
                    .setServiceName("DailyReportService")
                    .setControllerName("DailyReportController")
                    .setActionUrl("/appointmentmanagement/createDailyReport")
                    .setActionType("POST")
                    .setActionResponse("ERROR")
                    .build();
            loggingServiceBlockingStub.logRequest(loggingRequest);
            throw new UserNotFound("Patient not found.");
        } else if ( weeklyReportOptional.isEmpty()) {
            LoggingRequest loggingRequest = LoggingRequest.newBuilder()
                    .setServiceName("DailyReportService")
                    .setControllerName("DailyReportController")
                    .setActionUrl("/appointmentmanagement/createDailyReport")
                    .setActionType("POST")
                    .setActionResponse("ERROR")
                    .build();
            loggingServiceBlockingStub.logRequest(loggingRequest);
            throw new WeeklyReportNotFound("Weekly report not found.");
        }
        Patient patient = patientOptional.get();
        WeeklyReport weeklyReport = weeklyReportOptional.get();
        DailyReport dailyReport = new DailyReport(d.getContent(), patient, weeklyReport);
        DailyReport savedDailyReport = dailyReportRepo.save(dailyReport);
        LoggingRequest loggingRequest = LoggingRequest.newBuilder()
                .setServiceName("DailyReportService")
                .setControllerName("DailyReportController")
                .setActionUrl("/appointmentmanagement/createDailyReport")
                .setActionType("POST")
                .setActionResponse("SUCCESS")
                .build();
        loggingServiceBlockingStub.logRequest(loggingRequest);
        return dailyReportMapper.toDto(savedDailyReport);
    }

    public DailyReportDto updateDailyReport(@Valid DailyReportDto dto) {
        Optional<DailyReport> dailyReportOptional = dailyReportRepo.findById(dto.getDailyReportId());

        if (dailyReportOptional.isEmpty()) {
            LoggingRequest loggingRequest = LoggingRequest.newBuilder()
                    .setServiceName("DailyReportService")
                    .setControllerName("DailyReportController")
                    .setActionUrl("/appointmentmanagement/updateDailyReport")
                    .setActionType("PUT")
                    .setActionResponse("ERROR")
                    .build();
            loggingServiceBlockingStub.logRequest(loggingRequest);
            throw new DailyReportNotFound("Daily report not found.");
        }
        LoggingRequest loggingRequest = LoggingRequest.newBuilder()
                .setServiceName("DailyReportService")
                .setControllerName("DailyReportController")
                .setActionUrl("/appointmentmanagement/updateDailyReport")
                .setActionType("PUT")
                .setActionResponse("SUCCESS")
                .build();
        loggingServiceBlockingStub.logRequest(loggingRequest);
        DailyReport dailyReport = dailyReportOptional.get();
        dailyReport.setContent(dto.getContent());
        return dailyReportMapper.toDto(dailyReportRepo.save(dailyReport));
    }
@Transactional
    public void deleteDailyReport(String dailyReportId) {
    try {
        dailyReportRepo.deleteById(dailyReportId);
    } catch (Exception e) {
        LoggingRequest loggingRequest = LoggingRequest.newBuilder()
                .setServiceName("DailyReportService")
                .setControllerName("DailyReportController")
                .setActionUrl("/appointmentmanagement/deleteDailyReport")
                .setActionType("DELETE")
                .setActionResponse("ERROR")
                .build();
        loggingServiceBlockingStub.logRequest(loggingRequest);
        throw new DailyReportNotFound("Daily report not found.");
    }
    LoggingRequest loggingRequest = LoggingRequest.newBuilder()
            .setServiceName("DailyReportService")
            .setControllerName("DailyReportController")
            .setActionUrl("/appointmentmanagement/deleteDailyReport")
            .setActionType("DELETE")
            .setActionResponse("SUCCESS")
            .build();
    loggingServiceBlockingStub.logRequest(loggingRequest);
    }
}
