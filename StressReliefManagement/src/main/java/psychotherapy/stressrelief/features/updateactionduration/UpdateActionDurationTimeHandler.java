package psychotherapy.stressrelief.features.updateactionduration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;
import psychotherapy.stressrelief.datacontext.repository.StressReliefActionRepository;

import java.util.Optional;

@Service
public class UpdateActionDurationTimeHandler {
    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;
    public ResponseEntity<UpdateActionDurationTimeResponse> handle(UpdateActionDurationTimeRequest request) {
        Optional<StressReliefAction> entity = stressReliefActionRepository.findById(request.getStressReliefActionId());
        if (entity.isEmpty()) {
            throw new IllegalArgumentException("Action with provided id does not exist");
        }
        StressReliefAction action = entity.get();
        action.setDurationTime(request.getDurationTime());
        stressReliefActionRepository.save(action);
        return ResponseEntity.accepted().body(new UpdateActionDurationTimeResponse());
    }
}
