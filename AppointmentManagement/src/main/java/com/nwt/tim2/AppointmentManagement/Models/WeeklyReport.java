package com.nwt.tim2.AppointmentManagement.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "weekly_report")
public class WeeklyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "weekly_report_id", columnDefinition = "VARCHAR(64)")
    private String weeklyReportId;

    @Column(name = "content", columnDefinition = "VARCHAR(1024)")
    private String content;

    @ManyToOne
    @JoinColumn(name = "psychologist_id", referencedColumnName = "user_id")
    private Psychologist psychologist;
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id")
    private Patient patient;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;


    public WeeklyReport() {

    }

    public WeeklyReport(String content, Psychologist psychologist, Patient patient) {
        this.content=content;
        this.psychologist=psychologist;
        this.patient=patient;
        this.createdAt=LocalDateTime.now();
        System.out.println(this.createdAt);
    }
}
