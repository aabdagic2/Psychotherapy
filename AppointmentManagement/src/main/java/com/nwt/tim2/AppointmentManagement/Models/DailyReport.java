package com.nwt.tim2.AppointmentManagement.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "daily_report")
public class DailyReport {
    @Id
    @Column(name = "daily_report_id", columnDefinition = "VARCHAR(16)")
    private UUID dailyReportId;

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
        dailyReportId=UUID.randomUUID();
    }

    public DailyReport() {
        dailyReportId=UUID.randomUUID();
    }

    // Constructors, getters, and setters
}

