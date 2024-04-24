package psychotherapy.stressrelief.features.walk.createwalklog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class CreateWalkLogRequest {
    @NotBlank(message = "patientId is required")
    private String patientId;

    @Positive(message = "kilometeres must be positive")
    private double kilometers;
}
