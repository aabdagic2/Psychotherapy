package psychotherapy.stressrelief.datacontext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface StressReliefActionRepository extends JpaRepository<StressReliefAction, String> {
    public abstract List<StressReliefAction> findAllByPatientIdAndStartedAtAfter(String patientId, OffsetDateTime targetOffsetDateTime);
}
