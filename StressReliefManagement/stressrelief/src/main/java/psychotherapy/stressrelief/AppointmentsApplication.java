package psychotherapy.stressrelief;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import psychotherapy.stressrelief.datacontext.model.BreathControl;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;
import psychotherapy.stressrelief.datacontext.repository.StressReliefActionRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@SpringBootApplication
public class AppointmentsApplication implements CommandLineRunner {

	@Autowired
	private StressReliefActionRepository _stressReliefActionRepository;
	public static void main(String[] args) {
		SpringApplication.run(AppointmentsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		StressReliefAction action = new BreathControl();
		action.setPatientId(UUID.randomUUID());
		action.setStartedAt(LocalDateTime.now(ZoneOffset.UTC));
		action.setDurationTime(10);

		action = _stressReliefActionRepository.save(action);
		System.out.println(action);
	}
}
