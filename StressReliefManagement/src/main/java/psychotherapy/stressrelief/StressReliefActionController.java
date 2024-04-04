package psychotherapy.stressrelief;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psychotherapy.stressrelief.features.breathcontrol.createbreathcontrollog.CreateBreathControlLogHandler;
import psychotherapy.stressrelief.features.breathcontrol.createbreathcontrollog.CreateBreathControlLogRequest;
import psychotherapy.stressrelief.features.breathcontrol.createbreathcontrollog.CreateBreathControlLogResponse;
import psychotherapy.stressrelief.features.meditation.createmeditationlog.CreateMeditationLogHandler;
import psychotherapy.stressrelief.features.meditation.createmeditationlog.CreateMeditationLogRequest;
import psychotherapy.stressrelief.features.meditation.createmeditationlog.CreateMeditationLogResponse;
import psychotherapy.stressrelief.features.updateactionduration.UpdateActionDurationTimeHandler;
import psychotherapy.stressrelief.features.updateactionduration.UpdateActionDurationTimeRequest;
import psychotherapy.stressrelief.features.updateactionduration.UpdateActionDurationTimeResponse;
import psychotherapy.stressrelief.features.walk.createwalklog.CreateWalkLogHandler;
import psychotherapy.stressrelief.features.walk.createwalklog.CreateWalkLogRequest;
import psychotherapy.stressrelief.features.walk.createwalklog.CreateWalkLogResponse;

@RestController
@RequestMapping("/api/stressrelief")
public class StressReliefActionController {
    @Autowired
    private CreateMeditationLogHandler createMeditationLogHandler;
    @Autowired
    private CreateBreathControlLogHandler createBreathControlLogHandler;
    @Autowired
    private CreateWalkLogHandler createWalkLogHandler;
    @Autowired
    private UpdateActionDurationTimeHandler updateActionDurationTimeHandler;

    @PostMapping("/meditation")
    public ResponseEntity<CreateMeditationLogResponse> createMeditationLog(@RequestBody CreateMeditationLogRequest request) {
        return createMeditationLogHandler.handle(request);
    }

    @PostMapping("/walk")
    public ResponseEntity<CreateWalkLogResponse> createMeditationLog(@RequestBody CreateWalkLogRequest request) {
        return createWalkLogHandler.handle(request);
    }

    @PostMapping("/breathcontrol")
    public ResponseEntity<CreateBreathControlLogResponse> createMeditationLog(@RequestBody CreateBreathControlLogRequest request) {
        return createBreathControlLogHandler.handle(request);
    }

    @PatchMapping("/durationtime")
    public ResponseEntity<UpdateActionDurationTimeResponse> updateActionDurationTime(@RequestBody UpdateActionDurationTimeRequest request) {
        return updateActionDurationTimeHandler.handle(request);
    }
}
