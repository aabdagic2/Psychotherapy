package com.nwt.tim2.AppointmentManagement.Repos;


import com.nwt.tim2.AppointmentManagement.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message,Integer> {

}