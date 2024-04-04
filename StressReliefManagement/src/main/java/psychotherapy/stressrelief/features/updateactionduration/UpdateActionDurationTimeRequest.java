package psychotherapy.stressrelief.features.updateactionduration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class UpdateActionDurationTimeRequest {
    private String stressReliefActionId;
    private double durationTime;
}
