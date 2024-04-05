package com.nwt.tim2.AppointmentManagement.Dtos.Mapper;

import com.nwt.tim2.AppointmentManagement.Dtos.PatientDto;
import com.nwt.tim2.AppointmentManagement.Models.User;
import com.nwt.tim2.AppointmentManagement.Repos.PatientRepo;
import com.nwt.tim2.AppointmentManagement.Repos.PsychologistRepo;
import org.springframework.stereotype.Component;
import com.nwt.tim2.AppointmentManagement.Models.*;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Component
public class PatientMapper {
    private final PsychologistRepo psychologistRepo;

    public PatientMapper(PsychologistRepo psychologistRepo) {
        this.psychologistRepo = psychologistRepo;
    }

    public PatientDto toDto(Patient patient) {
        if (patient == null) {
            return null;
        }

        PatientDto dto = new PatientDto();
        dto.setSelectedPsychologistId(patient.getSelectedPsychologistId());
        dto.setAge(patient.getAge());
        return dto;
    }

    public Patient fromDto(PatientDto patientDto) {
        if (patientDto == null) {
            return null;
        }

        Patient patient = new Patient();
        Psychologist psychologist = psychologistRepo.findById(patientDto.getSelectedPsychologistId())
                .orElseThrow(() -> new IllegalArgumentException("Psychologist not found."));
        patient.setAge(patientDto.getAge());
        patient.setSelectedPsychologist(psychologist);
        return patient;
    }
}
