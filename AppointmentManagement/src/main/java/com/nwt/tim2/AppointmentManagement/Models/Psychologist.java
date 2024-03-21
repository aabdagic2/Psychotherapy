package com.nwt.tim2.AppointmentManagement.Models;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
@Table(name = "psychologist")
@DiscriminatorValue("psychologist")
public class Psychologist extends User {

    public Psychologist(UUID uuid) {
        super(uuid);
    }

    public Psychologist() {
        super(UUID.randomUUID());
    }
}