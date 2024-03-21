package psychotherapy.stressrelief.datacontext.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        schema = "sql11692975"
)
public class Walk extends StressReliefAction {
    private double kilometers;
}
