package psychotherapy.stressrelief.features.getactionlogs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;
import psychotherapy.stressrelief.datacontext.repository.StressReliefActionRepository;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class GetActionLogsHandler {
    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;

    public ResponseEntity<GetActionLogsResponse> handle(GetActionLogsRequest request) {
        OffsetDateTime targetDateTime = OffsetDateTime.now(ZoneOffset.UTC).with(LocalTime.MIN).minusDays(request.getDaysOffset());
        List<StressReliefAction> actions = stressReliefActionRepository.findAllByPatientIdAndStartedAtAfter(request.getPatientId(), targetDateTime);
        var response = new GetActionLogsResponse(actions);
        return ResponseEntity.ok(response);
    }
}
