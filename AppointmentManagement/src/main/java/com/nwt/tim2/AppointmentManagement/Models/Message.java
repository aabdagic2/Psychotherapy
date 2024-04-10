package com.nwt.tim2.AppointmentManagement.Models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Relation;
import java.util.Date;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id", columnDefinition = "VARCHAR(64)")
    private String messageId;

    @NotBlank(message = "Content cannot be blank")
    @Size(max = 1024, message = "Content length must be less than or equal to 1024 characters")
    @Column(name = "content")
    private String content;

    @NotNull(message = "Sender must be specified")
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id")
    private User sender;

    @NotNull(message = "Recipient must be specified")
    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "user_id")
    private User recipient;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Message(String content, Date createdAt, User sender, User recipient) {
        this.content = content;
        this.createdAt = createdAt;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Message() {
        messageId=UUID.randomUUID().toString();
    }

    public String getRecipient() {
        return recipient.getUserId();
    }

    public String getSender() {
        return sender.getUserId();
    }

    public String getContent() {
        return this.content;
    }
}
