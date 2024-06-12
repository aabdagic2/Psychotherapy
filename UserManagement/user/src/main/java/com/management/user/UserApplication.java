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
		var adminRoleExists = roleRepository.existsByName("Administrator");
		var psychologistRoleExists = roleRepository.existsByName("Psychologist");
		var patientRoleExists = roleRepository.existsByName("Patient");
		if (!adminRoleExists) {
			RoleEntity administratorRole = roleRepository.save(new RoleEntity("Administrator", "805f349f-dc46-4eaa-b4b2-20bb2a1e77ce"));
		}
		if (!psychologistRoleExists) {
			RoleEntity psychologistRole = roleRepository.save(new RoleEntity("Psychologist","b6fce159-9d1d-417a-93da-19111cad606c"));
		}
		if (!patientRoleExists) {
			RoleEntity patientRole = roleRepository.save(new RoleEntity("Patient", "e4aaef69-2a78-4ec2-8047-283fe0eedcb4"));
		}
	}
}
