package com.nwt.tim2.AppointmentManagement.Requests;

public class UpdateSessionRequest {
    private String newDay;
    private String newTime;

    public UpdateSessionRequest(String friday, String time) {
        newDay=friday;
        newTime=time;
    }

    public String getNewDay() {
        return newDay;
    }

    public String getNewTime() {
        return newTime;
    }
}
