package com.nwt.tim2.AppointmentManagement.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "quality_rate")
public class QualityRate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "quality_rate_id", columnDefinition = "VARCHAR(64)")
    private String qualityRateId;

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
    }

    public QualityRate() {
    }
}