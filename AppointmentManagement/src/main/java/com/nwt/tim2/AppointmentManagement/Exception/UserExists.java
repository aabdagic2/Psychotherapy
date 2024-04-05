package com.nwt.tim2.AppointmentManagement.Exception;

public class UserExists extends IllegalArgumentException{
    public UserExists(String m){
        super(m);
    }
}
