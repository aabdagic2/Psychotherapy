package com.nwt.tim2.AppointmentManagement.Models;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "weekly_report")
public class WeeklyReport {
    @Id
    @Column(name = "weekly_report_id", columnDefinition = "VARCHAR(16)")
    private UUID weeklyReportId;

    @Column(name = "content", columnDefinition = "VARCHAR(1024)")
    private String content;

    @ManyToOne
    @JoinColumn(name = "psychologist_id", referencedColumnName = "user_id")
    private Psychologist psychologist;

    public WeeklyReport(String sampleWeeklyReportContent, Psychologist psychologist) {
        weeklyReportId=UUID.randomUUID();
        this.psychologist=psychologist;
        this.content=sampleWeeklyReportContent;
    }

    public WeeklyReport() {
        weeklyReportId=UUID.randomUUID();
    }
}
