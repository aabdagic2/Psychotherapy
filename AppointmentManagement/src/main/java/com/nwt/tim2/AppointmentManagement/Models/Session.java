package com.nwt.tim2.AppointmentManagement.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Entity
@Getter
@Setter
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "session_id", columnDefinition = "VARCHAR(64)")
    private String qualityRateId;

    @ManyToOne
    @JoinColumn(name = "psychologist_id", referencedColumnName = "user_id")
    private Psychologist psychologist;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id")
    private Patient patient;
    @NotBlank(message = "Day cannot be empty")
    @Column(name = "day")
    private String day;
    @NotBlank(message = "Time cannot be empty")
    @Column(name="time")
    private String time;
    public Session(Psychologist psychologist, Patient patient, String day, String time) {
        this.psychologist=psychologist;
        this.patient=patient;
        this.day=day;
        this.time=time;
    }

    public Session() {
    }

    public void setPatient(Patient p) {
        this.patient=p;
    }

    public void setDay(String newDay) {
        this.day=newDay;
    }

    public void setTime(String time) {
        this.time=time;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return qualityRateId;
    }

    public String getPatientId() {
        return patient.getUserId();
    }


}