package com.nwt.tim2.AppointmentManagement.Exception;

public class WeeklyReportNotFound extends IllegalArgumentException{
    public WeeklyReportNotFound(String m){
        super(m);
    }
}