package com.nwt.tim2.AppointmentManagement.Dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class DailyReportDto {
    private String dailyReportId;
    private String content;
    private String patientId;
    private String weeklyReportId;
    private LocalDate createdAt;

}

