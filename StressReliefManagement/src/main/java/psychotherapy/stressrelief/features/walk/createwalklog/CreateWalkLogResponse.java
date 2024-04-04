package psychotherapy.stressrelief.features.walk.createwalklog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import psychotherapy.stressrelief.datacontext.model.Walk;

import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class CreateWalkLogResponse extends Walk  {
}
