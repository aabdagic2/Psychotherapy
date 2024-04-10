package com.nwt.tim2.AppointmentManagement.Exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Getter
@Setter
public class ValidationErrorResponse extends IllegalArgumentException{
    private String message;
    private List<Violation> violations;

    public ValidationErrorResponse(String message) {
        this.message = message;
        this.violations = new ArrayList<Violation>();
    }
    public void addToList(Violation v){
        violations.add(v);
    }
}

