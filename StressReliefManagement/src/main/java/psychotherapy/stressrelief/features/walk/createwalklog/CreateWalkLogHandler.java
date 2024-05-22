package psychotherapy.stressrelief.features.walk.createwalklog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;
import psychotherapy.stressrelief.datacontext.model.Walk;
import psychotherapy.stressrelief.datacontext.repository.StressReliefActionRepository;
import psychotherapy.stressrelief.features.patient.checkpatientexistence.CheckPatientExistenceHandler;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class CreateWalkLogHandler {
    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;
    @Autowired
    private CheckPatientExistenceHandler patientExistenceHandler;

    public ResponseEntity<CreateWalkLogResponse> handle(CreateWalkLogRequest request) {
        var patientExists = patientExistenceHandler.handle(request.getPatientId());
        if (!patientExists) {
            return ResponseEntity.notFound().build();
        }
        StressReliefAction action = CreateWalkLogMapper.INSTANCE.requestToEntity(request);
        action.setStartedAt(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC));
        Walk savedWalk = (Walk) stressReliefActionRepository.save(action);
        CreateWalkLogResponse response = CreateWalkLogMapper.INSTANCE.entityToResponse(savedWalk);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
