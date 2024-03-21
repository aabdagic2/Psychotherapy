package com.nwt.tim2.AppointmentManagement.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "patient")
@DiscriminatorValue("patient")
public class Patient extends User {
    @Column(name = "age")
    private Integer age;

    // Define the relationship with Psychologist
    @OneToOne
    @JoinColumn(name = "selected_psychologist_id", referencedColumnName = "user_id")
    private Psychologist selectedPsychologist;



    public Patient(UUID userId) {
        super(userId);
    }

    public Patient(UUID userId, int i, Psychologist psychologist) {
        super(userId);
        age=i;
        selectedPsychologist=psychologist;
    }

    public Patient() {
        super();
    }

    public void setSelectedPsychologist(Psychologist selectedPsychologist) {
        this.selectedPsychologist = selectedPsychologist;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    // Other patient-specific fields
}