package psychotherapy.stressrelief.features.getactionlogs;

import lombok.Getter;
import lombok.Setter;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;

import java.util.List;

@Getter
@Setter
public class GetActionLogsResponse {
    private List<StressReliefAction> stressReliefActionLogs;

    public GetActionLogsResponse(List<StressReliefAction> stressReliefActionLogs) {
        this.stressReliefActionLogs = stressReliefActionLogs;
    }
}
