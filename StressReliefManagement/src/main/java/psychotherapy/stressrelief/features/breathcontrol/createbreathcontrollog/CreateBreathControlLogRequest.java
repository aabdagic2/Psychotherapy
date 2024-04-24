package psychotherapy.stressrelief.features.breathcontrol.createbreathcontrollog;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;
import psychotherapy.stressrelief.datacontext.model.BreathControl;

import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class CreateBreathControlLogRequest {
    @NotBlank(message = "patientId is required")
    private String patientId;

    @Positive(message = "tempo must be positive value")
    private double tempo;
}
