package com.management.user;

import com.management.user.models.RoleEntity;
import com.management.user.models.UserEntity;
import com.management.user.repository.RoleRepository;
import com.management.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;



@SpringBootApplication


public class UserApplication implements CommandLineRunner {
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	public UserApplication(RoleRepository roleRepository, UserRepository userRepository) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		RoleEntity administratorRole = roleRepository.save(new RoleEntity("Administrator"));
		RoleEntity psychologistRole = roleRepository.save(new RoleEntity("Psychologist"));
		RoleEntity patientRole = roleRepository.save(new RoleEntity("Patient"));

		UserEntity adminUser = new UserEntity("admin", "admin@example.com", "Admin", "admin123");
		adminUser.setRole(administratorRole);
		userRepository.save(adminUser);

		UserEntity psychologistUser = new UserEntity("psychologist", "psychologist@example.com", "Psychologist", "psychologist123");
		psychologistUser.setRole(psychologistRole);
		userRepository.save(psychologistUser);

		UserEntity patientUser = new UserEntity("patient", "patient@example.com", "Patient", "patient123");
		patientUser.setRole(patientRole);
		userRepository.save(patientUser);
	}
}
