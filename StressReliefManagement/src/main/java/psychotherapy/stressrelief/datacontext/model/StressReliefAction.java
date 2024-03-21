package psychotherapy.stressrelief.datacontext.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        schema = "sql11692975"
)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class StressReliefAction {
    @Id
//    @Column(
//            nullable = false
//    )
    @UuidGenerator
    private UUID stressReliefActionId;

    private UUID patientId;

    @Column(
            columnDefinition = "DATETIME"
    )
    private LocalDateTime startedAt;

    private double durationTime;
}
