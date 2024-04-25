package psychotherapy.stressrelief.features.meditation.createmeditationlog;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import psychotherapy.stressrelief.datacontext.model.Meditation;

import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class CreateMeditationLogRequest {
    @NotBlank(message = "patientId is required")
    private String patientId;

    private boolean music;
}
