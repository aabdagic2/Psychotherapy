package com.nwt.tim2.AppointmentManagement.Requests;

public class DeleteSessionRequest {
    private String psychologistId;
    private String day;
    private String time;
    public DeleteSessionRequest(String ps, String day, String time){
        psychologistId=ps;
        this.day=day;
        this.time=time;
    }

    public DeleteSessionRequest() {

    }

    public String getPsychologistId() {
        return  psychologistId;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public void setPsychologistId(String psychologistId) {
        this.psychologistId=psychologistId;
    }

    public void setDay(String day) {
        this.day=day;
    }

    public void setTime(String time) {
        this.time=time;
    }
}

