package com.nwt.tim2.AppointmentManagement.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "daily_report")
public class DailyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "daily_report_id", columnDefinition = "VARCHAR(64)")
    private String dailyReportId;

    @Column(name = "content", columnDefinition = "VARCHAR(1024)")
    private String content;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "weekly_report_id", referencedColumnName = "weekly_report_id")
    private WeeklyReport weeklyReport;

    public DailyReport(String sampleContent, Patient patient, WeeklyReport weeklyReport) {
        content=sampleContent;
        this.patient=patient;
        this.weeklyReport=weeklyReport;
    }

    public DailyReport() {
    }

    // Constructors, getters, and setters
}

