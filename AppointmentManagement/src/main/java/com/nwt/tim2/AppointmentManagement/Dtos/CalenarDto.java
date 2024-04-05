package com.nwt.tim2.AppointmentManagement.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CalenarDto {
    private Long id;
    private String psychologistEmail;
    private String patientEmail;
    private String meetLink;
    private LocalDateTime dateTime;
    private String subject;
    private String organization;
}
