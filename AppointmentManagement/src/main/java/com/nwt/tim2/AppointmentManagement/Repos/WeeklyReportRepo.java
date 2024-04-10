package com.nwt.tim2.AppointmentManagement.Repos;


import com.nwt.tim2.AppointmentManagement.Models.Psychologist;
import com.nwt.tim2.AppointmentManagement.Models.WeeklyReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WeeklyReportRepo extends JpaRepository<WeeklyReport,String> {
    List<WeeklyReport> findByPsychologist(Psychologist psychologist);

    boolean existsByPsychologistAndCreatedAtAfter(Psychologist psychologist, LocalDateTime latestSessionDateTime);
}
