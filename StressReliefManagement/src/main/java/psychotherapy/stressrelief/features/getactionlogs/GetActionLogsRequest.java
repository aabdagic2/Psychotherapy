package psychotherapy.stressrelief.features.getactionlogs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class GetActionLogsRequest {
    private String patientId;
    private int daysOffset;

    public GetActionLogsRequest(String patientId, int daysOffset) {
        this.patientId = patientId;
        this.daysOffset = daysOffset;
    }
}
