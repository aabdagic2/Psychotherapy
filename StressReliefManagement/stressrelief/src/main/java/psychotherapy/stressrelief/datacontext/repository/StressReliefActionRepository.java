package psychotherapy.stressrelief.datacontext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;

import java.util.UUID;

public interface StressReliefActionRepository extends JpaRepository<StressReliefAction, UUID> {
}
