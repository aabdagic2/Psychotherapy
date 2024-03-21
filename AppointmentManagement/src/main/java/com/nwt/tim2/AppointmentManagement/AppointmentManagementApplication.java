package com.nwt.tim2.AppointmentManagement;



import com.nwt.tim2.AppointmentManagement.Repos.*;
import com.nwt.tim2.AppointmentManagement.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
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

	public static void main(String[] args) {
		SpringApplication.run(AppointmentManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Psychologist psychologist = new Psychologist();
		psychologist=psychologistRepository.save(psychologist);
		Patient patient = new Patient(30,psychologist);
		patientRepository.save(patient);
		Message message = new Message("Sample message content", new Date(), patient, psychologist);
		messageRepository.save(message);
		QualityRate qualityRate = new QualityRate(psychologist, patient, 5);
		qualityRateRepository.save(qualityRate);
		WeeklyReport weeklyReport = new WeeklyReport("Sample weekly report content", psychologist);
		weeklyReportRepository.save(weeklyReport);
		DailyReport dailyReport = new DailyReport("Sample content", patient, weeklyReport);
		dailyReportRepository.save(dailyReport);

	}
}

