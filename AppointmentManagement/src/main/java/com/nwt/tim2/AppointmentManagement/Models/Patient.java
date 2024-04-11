package com.nwt.tim2.AppointmentManagement.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import java.util.UUID;

@Entity
@Table(name = "patient")
@DiscriminatorValue("patient")
@Getter
@Setter
public class Patient extends User {
    @NotNull
    @Range(min = 13, max = 100, message = "Age must be between 13 and 100")
    @Column(name = "age")
    private Integer age;
    @OneToOne
    @JoinColumn(name = "selected_psychologist_id", referencedColumnName = "user_id")
    private Psychologist selectedPsychologist;
    public Patient(UUID userId) {
        super();
    }

    public Patient(UUID userId, int i, Psychologist psychologist) {
        super(userId.toString());
        age=i;
        selectedPsychologist=psychologist;
    }

    public Patient() {
        super();
    }

    public Patient(int i, Psychologist psychologist) {
        super();
        this.selectedPsychologist=psychologist;
        this.age=i;
    }

    public void setSelectedPsychologist(Psychologist selectedPsychologist) {
        this.selectedPsychologist = selectedPsychologist;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSelectedPsychologistId() {
        if(selectedPsychologist!=null){
        return selectedPsychologist.getUserId();}
        else{
            return null;
        }
    }


}