package com.nwt.tim2.AppointmentManagement.Requests;
public class CreateSessionRequest {
    private String psychologistId;
    private String day;
    private String time;

    public CreateSessionRequest(String psychologist123, String monday, String time) {
        psychologistId=psychologist123;
        day=monday;
        this.time=time;
    }

    public String getPsychologistId() {
        return psychologistId;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}


