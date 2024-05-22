package psychotherapy.stressrelief.features.meditation.createmeditationlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import psychotherapy.stressrelief.datacontext.model.Meditation;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;
import psychotherapy.stressrelief.datacontext.repository.StressReliefActionRepository;
import psychotherapy.stressrelief.features.patient.checkpatientexistence.CheckPatientExistenceHandler;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class CreateMeditationLogHandler {
    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;
    @Autowired
    private CheckPatientExistenceHandler patientExistenceHandler;

    public ResponseEntity<CreateMeditationLogResponse> handle(CreateMeditationLogRequest request) {
        var patientExists = patientExistenceHandler.handle(request.getPatientId());
        if (!patientExists) {
            return ResponseEntity.notFound().build();
        }
        StressReliefAction action = CreateMeditationLogMapper.INSTANCE.requestToEntity(request);
        action.setStartedAt(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC));
        Meditation savedMeditation = (Meditation) stressReliefActionRepository.save(action);
        CreateMeditationLogResponse response = CreateMeditationLogMapper.INSTANCE.entityToResponse(savedMeditation);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
