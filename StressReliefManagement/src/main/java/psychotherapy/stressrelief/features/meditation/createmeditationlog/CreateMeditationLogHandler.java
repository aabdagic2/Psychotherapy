package psychotherapy.stressrelief.features.meditation.createmeditationlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import psychotherapy.stressrelief.datacontext.model.Meditation;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;
import psychotherapy.stressrelief.datacontext.repository.StressReliefActionRepository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class CreateMeditationLogHandler {
    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;
    public ResponseEntity<CreateMeditationLogResponse> handle(CreateMeditationLogRequest request) {
        StressReliefAction action = CreateMeditationLogMapper.INSTANCE.requestToEntity(request);
        action.setStartedAt(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC));
        Meditation savedMeditation = (Meditation) stressReliefActionRepository.save(action);
        CreateMeditationLogResponse response = CreateMeditationLogMapper.INSTANCE.entityToResponse(savedMeditation);
        return ResponseEntity.ok(response);
    }
}
