package com.nwt.tim2.AppointmentManagement.Requests;

public class AddPatientRequest {
    private String psychologistId;
    private String day;
    private String time;
    private String patientId;

    public AddPatientRequest(String psychologist123, String monday, String time, String patient123) {
        psychologistId=psychologist123;
        day=monday;
        this.time=time;
        patientId=patient123;
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

    public String getPatientId() {
        return patientId;
    }
}
