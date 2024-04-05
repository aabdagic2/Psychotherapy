package com.nwt.tim2.AppointmentManagement.Exception;

public class SessionNotFound extends IllegalArgumentException{
    public SessionNotFound(String m){
        super(m);
    }
}
