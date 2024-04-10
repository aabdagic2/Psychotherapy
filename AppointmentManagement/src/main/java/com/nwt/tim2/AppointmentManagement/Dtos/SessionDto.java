package com.nwt.tim2.AppointmentManagement.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionDto {
    private String sessionId;
    private String psychologistId;
    private String patientId;
    private String day;
    private String time;

}
