package com.nwt.tim2.AppointmentManagement.Dtos.Mapper;

import com.nwt.tim2.AppointmentManagement.Dtos.QualityRateDto;
import com.nwt.tim2.AppointmentManagement.Models.Patient;
import com.nwt.tim2.AppointmentManagement.Models.Psychologist;
import com.nwt.tim2.AppointmentManagement.Models.QualityRate;
import com.nwt.tim2.AppointmentManagement.Repos.PatientRepo;
import com.nwt.tim2.AppointmentManagement.Repos.PsychologistRepo;
import com.nwt.tim2.AppointmentManagement.Service.PatientService;
import com.nwt.tim2.AppointmentManagement.Service.PsychologistService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class QualityRateMapper {
    private final PsychologistRepo psychologistService;
    private final PatientRepo patientService;

    public QualityRateMapper(PsychologistRepo psychologistService, PatientRepo patientService) {
        this.psychologistService = psychologistService;
        this.patientService = patientService;
    }

    public QualityRateDto toDto(QualityRate qualityRate) {
        if (qualityRate == null) {
            return null;
        }

        QualityRateDto dto = new QualityRateDto();
        dto.setQualityRateId(qualityRate.getQualityRateId());
        dto.setPsychologistId(qualityRate.getPsychologist().getUserId());
        dto.setPatientId(qualityRate.getPatient().getUserId());
        dto.setRate(qualityRate.getRate());
        dto.setCreatedAt(qualityRate.getCreatedAt());
        return dto;
    }

    public QualityRate fromDto(QualityRateDto qualityRateDto) {
        if (qualityRateDto == null) {
            return null;
        }

        QualityRate qualityRate = new QualityRate();
        qualityRate.setQualityRateId(qualityRateDto.getQualityRateId());
        Psychologist psychologist = psychologistService.findById(qualityRateDto.getPsychologistId()).orElse(null);
        Patient patient = patientService.findById(qualityRateDto.getPatientId()).orElse(null);
        qualityRate.setPsychologist(psychologist);
        qualityRate.setPatient(patient);
        qualityRate.setRate(qualityRateDto.getRate());
        qualityRate.setCreatedAt(qualityRateDto.getCreatedAt());
        return qualityRate;
    }
}
