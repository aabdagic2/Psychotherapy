package com.management.user.Request;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PatientRequest {

    private String userId;
    private Integer age;
    private String selectedPsychologistId;
}
