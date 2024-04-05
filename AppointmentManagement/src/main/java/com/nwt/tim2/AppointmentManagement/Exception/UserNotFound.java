package com.nwt.tim2.AppointmentManagement.Exception;

public class UserNotFound extends IllegalArgumentException{
    public UserNotFound(String m){
        super(m);
    }
}
