package com.nwt.tim2.AppointmentManagement.Dtos.Mapper;

import com.nwt.tim2.AppointmentManagement.Dtos.WeeklyReportDto;
import com.nwt.tim2.AppointmentManagement.Models.Patient;
import com.nwt.tim2.AppointmentManagement.Models.Psychologist;
import com.nwt.tim2.AppointmentManagement.Models.WeeklyReport;
import com.nwt.tim2.AppointmentManagement.Repos.PatientRepo;
import com.nwt.tim2.AppointmentManagement.Repos.PsychologistRepo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class WeeklyReportMapper {
    private final PsychologistRepo psychologistService;
    private final PatientRepo patientService;

    public WeeklyReportMapper(PsychologistRepo psychologistService, PatientRepo patientService) {
        this.psychologistService = psychologistService;
        this.patientService = patientService;
    }

    public WeeklyReportDto toDto(WeeklyReport weeklyReport) {
        if (weeklyReport == null) {
            return null;
        }

        WeeklyReportDto dto = new WeeklyReportDto();
        dto.setWeeklyReportId(weeklyReport.getWeeklyReportId());
        dto.setContent(weeklyReport.getContent());
        dto.setPsychologistId(weeklyReport.getPsychologist().getUserId());
        dto.setPatientId(weeklyReport.getPatient().getUserId());
        dto.setCreatedAt(weeklyReport.getCreatedAt());
        return dto;
    }

    public WeeklyReport fromDto(WeeklyReportDto weeklyReportDto) {
        if (weeklyReportDto == null) {
            return null;
        }

        WeeklyReport weeklyReport = new WeeklyReport();
        weeklyReport.setWeeklyReportId(weeklyReportDto.getWeeklyReportId());
        Psychologist psychologist = psychologistService.findById(weeklyReportDto.getPsychologistId()).orElse(null);
        Patient patient = patientService.findById(weeklyReportDto.getPatientId()).orElse(null);
        weeklyReport.setPsychologist(psychologist);
        weeklyReport.setPatient(patient);
        weeklyReport.setContent(weeklyReportDto.getContent());
        weeklyReport.setCreatedAt(weeklyReportDto.getCreatedAt());
        return weeklyReport;
    }
}
