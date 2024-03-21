package com.nwt.tim2.AppointmentManagement.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @Column(name = "user_id", columnDefinition = "VARCHAR(64)")
    private UUID userId;

    public User(UUID uuid) {
        this.userId=uuid;

    }

    public User() {
        this.userId=UUID.randomUUID();
    }

    public UUID getUserId() {
        return userId;
    }
}
