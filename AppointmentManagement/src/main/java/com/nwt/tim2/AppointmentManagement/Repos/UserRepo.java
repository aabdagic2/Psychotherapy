package com.nwt.tim2.AppointmentManagement.Repos;



import com.nwt.tim2.AppointmentManagement.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {

}
