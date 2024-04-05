package com.nwt.tim2.AppointmentManagement.Dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class MessageDto {
    private String messageId;
    private String content;
    private Date createdAt;
    private String senderId;
    private String recipientId;

}

