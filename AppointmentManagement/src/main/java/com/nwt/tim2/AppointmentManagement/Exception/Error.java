package com.nwt.tim2.AppointmentManagement.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private Integer code;
    private String message;
    private Date timeStamp;
}
