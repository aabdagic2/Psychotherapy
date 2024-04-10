package com.nwt.tim2.AppointmentManagement.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
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
    @NotNull(message = "Rate cannot be null")
    @Min(value = 1, message = "Rate must be at least 1")
    @Max(value = 5, message = "Rate cannot be greater than 5")
    @Column(name = "rate")
    private Integer rate;
    @NotNull(message = "Creation date cannot be null")
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    public QualityRate(Psychologist psychologist, Patient patient, int i) {
        this.psychologist=psychologist;
        this.patient=patient;
        this.rate=i;
        this.createdAt=LocalDateTime.now();
    }

    public QualityRate() {
    }
}