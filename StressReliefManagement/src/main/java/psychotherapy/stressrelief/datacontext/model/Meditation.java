package psychotherapy.stressrelief.datacontext.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@Entity
@Table
public class Meditation extends StressReliefAction{
    private boolean music;

    public Meditation() {
        super();
    }
}
