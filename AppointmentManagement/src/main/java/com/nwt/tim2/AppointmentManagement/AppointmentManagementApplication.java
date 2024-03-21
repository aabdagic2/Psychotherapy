package com.nwt.tim2.AppointmentManagement;



import com.nwt.tim2.AppointmentManagement.Repos.*;
import com.nwt.tim2.AppointmentManagement.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

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
		psychologistRepository.save(psychologist);
		// Inserting data into the Patient table
		Patient patient = new Patient();
		patient.setAge(30);
		patient.setSelectedPsychologist(psychologist);
		patientRepository.save(patient);
		// Inserting data into the Message table
		Message message = new Message("Sample message content", new Date(), patient, psychologist);
		messageRepository.save(message);

		// Inserting data into the QualityRate table
		QualityRate qualityRate = new QualityRate(psychologist, patient, 5);
		qualityRateRepository.save(qualityRate);

		// Inserting data into the WeeklyReport table
		WeeklyReport weeklyReport = new WeeklyReport("Sample weekly report content", psychologist);
		weeklyReportRepository.save(weeklyReport);

		// Inserting data into the DailyReport table
		DailyReport dailyReport = new DailyReport("Sample content", patient, weeklyReport);
		dailyReportRepository.save(dailyReport);

	}
}

