package psychotherapy.stressrelief.features.breathcontrol.createbreathcontrollog;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import psychotherapy.stressrelief.datacontext.model.BreathControl;

import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class CreateBreathControlLogRequest {
    private UUID patientId;

    private double tempo;
}
