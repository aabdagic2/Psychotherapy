package psychotherapy.stressrelief.features.breathcontrol.createbreathcontrollog;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import psychotherapy.stressrelief.datacontext.model.BreathControl;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;
import psychotherapy.stressrelief.datacontext.repository.StressReliefActionRepository;
import psychotherapy.stressrelief.features.patient.checkpatientexistence.CheckPatientExistenceHandler;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@Validated
public class CreateBreathControlLogHandler {
    @Autowired
    private CheckPatientExistenceHandler patientExistenceHandler;

    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;

    public ResponseEntity<CreateBreathControlLogResponse> handle(CreateBreathControlLogRequest request) {
        var patientExists = patientExistenceHandler.handle(request.getPatientId());
        if (!patientExists) {
            return ResponseEntity.notFound().build();
        }
        StressReliefAction action = CreateBreathControlLogMapper.INSTANCE.requestToEntity(request);
        action.setStartedAt(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC));
        BreathControl savedBreathControl = (BreathControl) stressReliefActionRepository.save(action);
        CreateBreathControlLogResponse response = CreateBreathControlLogMapper.INSTANCE.entityToResponse(savedBreathControl);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
