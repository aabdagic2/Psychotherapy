package com.nwt.tim2.AppointmentManagement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.nwt.tim2.AppointmentManagement.Repos.*;
import com.nwt.tim2.AppointmentManagement.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
public class AppointmentManagementApplication implements CommandLineRunner {

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private DailyReportRepo dailyReportRepository;

	@Autowired
	private MessageRepo messageRepository;

	@Autowired
	private PatientRepo patientRepository;

	@Autowired
	private PsychologistRepo psychologistRepository;

	@Autowired
	private QualityRateRepo qualityRateRepository;

	@Autowired
	private WeeklyReportRepo weeklyReportRepository;
	@Autowired
	private SessionRepo sessionRepository;

	public static void main(String[] args) {
		SpringApplication.run(AppointmentManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Psychologist psychologist = new Psychologist();
		psychologist.setUserId(UUID.randomUUID().toString());
		psychologist=psychologistRepository.save(psychologist);
		Patient patient = new Patient(30,psychologist);
		patient.setUserId(UUID.randomUUID().toString());
		patientRepository.save(patient);
		Message message = new Message("Sample message content", new Date(), patient, psychologist);
		messageRepository.save(message);
		QualityRate qualityRate = new QualityRate(psychologist, patient, 5);
		qualityRateRepository.save(qualityRate);
		WeeklyReport weeklyReport = new WeeklyReport("Sample weekly report content", psychologist,patient);
		weeklyReportRepository.save(weeklyReport);
		DailyReport dailyReport = new DailyReport("Sample content", patient, weeklyReport);
		dailyReportRepository.save(dailyReport);
		Session session = new Session(psychologist, patient, "Monday", "09:00");
		sessionRepository.save(session);

	}

}

