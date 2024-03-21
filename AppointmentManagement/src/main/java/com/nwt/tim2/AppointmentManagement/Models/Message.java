package com.nwt.tim2.AppointmentManagement.Models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id", columnDefinition = "VARCHAR(64)")
    private String messageId;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id") // Change 'id' to 'userId'
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "user_id") // Change 'id' to 'userId'
    private User recipient;
    public Message(String content, Date createdAt, User sender, User recipient) {
        this.content = content;
        this.createdAt = createdAt;
        this.sender = sender;
        this.recipient = recipient;
        messageId=UUID.randomUUID().toString();
    }

    public Message() {
        messageId=UUID.randomUUID().toString();
    }
}

