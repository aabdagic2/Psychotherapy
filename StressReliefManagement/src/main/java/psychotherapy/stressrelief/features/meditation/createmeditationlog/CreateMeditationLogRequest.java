package psychotherapy.stressrelief.features.meditation.createmeditationlog;

import jakarta.persistence.Column;
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
    private UUID patientId;

    private boolean music;
}
