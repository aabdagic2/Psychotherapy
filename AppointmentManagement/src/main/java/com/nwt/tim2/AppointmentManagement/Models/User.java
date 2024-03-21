package com.nwt.tim2.AppointmentManagement.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", columnDefinition = "VARCHAR(64)")
    private String userId;

    public User(String uuid) {

    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }
}
