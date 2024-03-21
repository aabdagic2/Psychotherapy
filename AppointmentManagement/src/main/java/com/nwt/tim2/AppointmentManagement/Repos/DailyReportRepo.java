package com.nwt.tim2.AppointmentManagement.Repos;


import com.nwt.tim2.AppointmentManagement.Models.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyReportRepo extends JpaRepository<DailyReport,Integer> {

}
