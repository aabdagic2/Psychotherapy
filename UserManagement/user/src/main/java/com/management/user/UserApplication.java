package com.management.user;

import com.management.user.accessingdatamysql.Model.RoleEntity;
import com.management.user.accessingdatamysql.Model.UserEntity;
import com.management.user.accessingdatamysql.Model.UserRoleEntity;
import com.management.user.accessingdatamysql.Repository.RoleRepository;
import com.management.user.accessingdatamysql.Repository.UserRepository;
import com.management.user.accessingdatamysql.Repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class UserApplication implements CommandLineRunner{
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	private final UserRoleRepository userRoleRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
	public UserApplication(RoleRepository roleRepository, UserRepository userRepository, UserRoleRepository userRoleRepository) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	public void run(String... args) throws Exception {
	   RoleEntity administratorRole = roleRepository.save(new RoleEntity("Administrator"));
	   RoleEntity psychologistRole = roleRepository.save(new RoleEntity("Psychologist"));
	   RoleEntity patientRole = roleRepository.save(new RoleEntity("Patient"));

	   UserEntity adminUser = new UserEntity("admin@example.com", "Admin", "admin123");

	   userRepository.save(adminUser);
	   UserRoleEntity adminUserRole = new UserRoleEntity();
	   adminUserRole.setUser(adminUser);
	   adminUserRole.setRole(administratorRole);


	   UserEntity psychologistUser = new UserEntity("psychologist@example.com", "Psychologist", "psychologist123");
	   userRepository.save(psychologistUser);
	   UserRoleEntity psychologistUserRole = new UserRoleEntity();
	   psychologistUserRole.setUser(psychologistUser);
	   psychologistUserRole.setRole(psychologistRole);

       UserEntity patientUser = new UserEntity("patient@example.com", "Patient", "patient123");
	   userRepository.save(patientUser);
	   UserRoleEntity patientUserRole = new UserRoleEntity();
	   patientUserRole.setUser(patientUser);
	   patientUserRole.setRole(patientRole);

	   userRoleRepository.save((patientUserRole));
	   userRoleRepository.save(psychologistUserRole);
	   userRoleRepository.save((adminUserRole));


   }
}
