package com.nwt.tim2.AppointmentManagement.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Entity
@Getter
@Setter
@Table(name = "psychologist")
@DiscriminatorValue("psychologist")
public class Psychologist extends User {

    public Psychologist(UUID uuid) {
        super();
    }

    public Psychologist() {
        super();
    }
}