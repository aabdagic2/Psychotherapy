package psychotherapy.stressrelief.features.walk.createwalklog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;
import psychotherapy.stressrelief.datacontext.model.Walk;
import psychotherapy.stressrelief.datacontext.repository.StressReliefActionRepository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class CreateWalkLogHandler {
    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;
    public ResponseEntity<CreateWalkLogResponse> handle(CreateWalkLogRequest request) {
        StressReliefAction action = CreateWalkLogMapper.INSTANCE.requestToEntity(request);
        action.setStartedAt(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC));
        Walk savedWalk = (Walk) stressReliefActionRepository.save(action);
        CreateWalkLogResponse response = CreateWalkLogMapper.INSTANCE.entityToResponse(savedWalk);
        return ResponseEntity.ok(response);
    }
}
