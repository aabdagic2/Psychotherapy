package com.nwt.tim2.AppointmentManagement.Repos;


import com.nwt.tim2.AppointmentManagement.Models.DailyReport;
import com.nwt.tim2.AppointmentManagement.Models.Patient;
import com.nwt.tim2.AppointmentManagement.Models.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyReportRepo extends JpaRepository<DailyReport, String> {

    List<DailyReport> findByPatientAndCreatedAtAfter(Patient patient, LocalDate date);
}
