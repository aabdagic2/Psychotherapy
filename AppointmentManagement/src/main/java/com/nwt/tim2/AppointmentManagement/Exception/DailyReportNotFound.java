package com.nwt.tim2.AppointmentManagement.Exception;

import com.nwt.tim2.AppointmentManagement.Dtos.DailyReportDto;

public class DailyReportNotFound extends IllegalArgumentException{
    public DailyReportNotFound(String m){
        super(m);
    }
}