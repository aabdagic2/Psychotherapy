package psychotherapy.stressrelief.features.breathcontrol.createbreathcontrollog;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import psychotherapy.stressrelief.datacontext.model.BreathControl;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;
import psychotherapy.stressrelief.datacontext.repository.StressReliefActionRepository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@Validated
public class CreateBreathControlLogHandler {
    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;
    public ResponseEntity<CreateBreathControlLogResponse> handle(CreateBreathControlLogRequest request) {
        StressReliefAction test = null;
        System.out.println(test.getDurationTime());
        StressReliefAction action = CreateBreathControlLogMapper.INSTANCE.requestToEntity(request);
        action.setStartedAt(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC));
        BreathControl savedBreathControl = (BreathControl) stressReliefActionRepository.save(action);
        CreateBreathControlLogResponse response = CreateBreathControlLogMapper.INSTANCE.entityToResponse(savedBreathControl);
        return ResponseEntity.ok(response);
    }
}
