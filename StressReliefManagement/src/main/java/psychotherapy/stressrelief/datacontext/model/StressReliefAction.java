package psychotherapy.stressrelief.datacontext.model;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class StressReliefAction {
    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String stressReliefActionId;

    @Column(columnDefinition = "VARCHAR(36)")
    private String patientId;

    @Column(
            columnDefinition = "DATETIME"
    )
    private OffsetDateTime startedAt;

    private double durationTime;

    public StressReliefAction() {
        this.stressReliefActionId = UUID.randomUUID().toString();
    }
    public StressReliefAction(String patientId, OffsetDateTime startedAt, double durationTime) {
        this.patientId = patientId;
        this.startedAt = startedAt;
        this.durationTime = durationTime;
    }
}
