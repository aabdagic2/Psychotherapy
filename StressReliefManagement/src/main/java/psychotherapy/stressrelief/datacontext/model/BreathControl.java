package psychotherapy.stressrelief.datacontext.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@Entity
@Table
public class BreathControl extends StressReliefAction {
    private double tempo;
    public BreathControl() {
        super();
    }
}
