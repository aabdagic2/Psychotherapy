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
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


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
		RoleEntity administratorRole = roleRepository.save(new RoleEntity("Administrator", "805f349f-dc46-4eaa-b4b2-20bb2a1e77ce"));
		RoleEntity psychologistRole = roleRepository.save(new RoleEntity("Psychologist","b6fce159-9d1d-417a-93da-19111cad606c"));
		RoleEntity patientRole = roleRepository.save(new RoleEntity("Patient", "e4aaef69-2a78-4ec2-8047-283fe0eedcb4"));
/*
		UserEntity adminUser = new UserEntity("admin", "admin@example.com", "Admin", "admin123", roleRepository.findById("286458d0-5913-49ef-a8f0-d634a0915d1a").orElse(null));
		adminUser.setRole(administratorRole);
		userRepository.save(adminUser);

		UserEntity psychologistUser = new UserEntity("psychologist", "psychologist@example.com", "Psychologist", "psychologist123", roleRepository.findById("286458d0-5913-49ef-a8f0-d634a0915d1a").orElse(null));
		psychologistUser.setRole(psychologistRole);
		userRepository.save(psychologistUser);

		UserEntity patientUser = new UserEntity("patient", "patient@example.com", "Patient", "patient123", roleRepository.findById("286458d0-5913-49ef-a8f0-d634a0915d1a").orElse(null));
		patientUser.setRole(patientRole);
		userRepository.save(patientUser);*/
	}
}
