package com.nwt.tim2.AppointmentManagement.Repos;


import com.nwt.tim2.AppointmentManagement.Models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient,Integer> {

}