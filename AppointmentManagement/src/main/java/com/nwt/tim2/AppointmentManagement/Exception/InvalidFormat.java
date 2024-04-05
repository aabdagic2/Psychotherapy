package com.nwt.tim2.AppointmentManagement.Exception;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InvalidFormat extends RuntimeException{
    public InvalidFormat(String m){
        super(m);
    }
}
