package psychotherapy.stressrelief.features.walk.createwalklog;

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
    private UUID patientId;

    private double kilometers;
}
