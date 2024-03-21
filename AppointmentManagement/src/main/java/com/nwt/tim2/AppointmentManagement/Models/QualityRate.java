package com.nwt.tim2.AppointmentManagement.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "quality_rate")
public class QualityRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quality_rate_id", columnDefinition = "VARCHAR(16)")
    private UUID qualityRateId;

    @ManyToOne
    @JoinColumn(name = "psychologist_id", referencedColumnName = "user_id")
    private Psychologist psychologist;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id")
    private Patient patient;

    @Column(name = "rate")
    private Integer rate;

    public QualityRate(Psychologist psychologist, Patient patient, int i) {
        this.psychologist=psychologist;
        this.patient=patient;
        this.rate=i;
        qualityRateId=UUID.randomUUID();
    }

    public QualityRate() {
        qualityRateId=UUID.randomUUID();
    }
}