package com.nwt.tim2.AppointmentManagement.Dtos.Mapper;

import com.nwt.tim2.AppointmentManagement.Dtos.PsychologistDto;
import com.nwt.tim2.AppointmentManagement.Models.Psychologist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class PsychologistMapper {

    public PsychologistDto toDto(Psychologist psychologist) {
        if (psychologist == null) {
            return null;
        }

        PsychologistDto dto = new PsychologistDto();
        dto.setUserId(psychologist.getUserId());
        return dto;
    }

    public Psychologist fromDto(PsychologistDto psychologistDto) {
        if (psychologistDto == null) {
            return null;
        }

        Psychologist psychologist = new Psychologist();
        psychologist.setUserId(psychologistDto.getUserId());
        return psychologist;
    }
}

