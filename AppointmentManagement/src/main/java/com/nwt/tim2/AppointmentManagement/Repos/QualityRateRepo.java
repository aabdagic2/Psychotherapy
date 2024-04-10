package com.nwt.tim2.AppointmentManagement.Repos;


import com.nwt.tim2.AppointmentManagement.Models.Patient;
import com.nwt.tim2.AppointmentManagement.Models.Psychologist;
import com.nwt.tim2.AppointmentManagement.Models.QualityRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface QualityRateRepo extends JpaRepository<QualityRate, String> {
    Double findAverageQualityRateByPsychologist(Psychologist psychologist);

    boolean existsByPatientAndCreatedAtAfter(Patient patient, LocalDateTime date);
}