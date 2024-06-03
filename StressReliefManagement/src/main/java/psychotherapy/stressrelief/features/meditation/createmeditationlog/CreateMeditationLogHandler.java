package psychotherapy.stressrelief.features.meditation.createmeditationlog;

import ba.unsa.etf.pnwt.proto.LoggingRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import psychotherapy.stressrelief.datacontext.model.Meditation;
import psychotherapy.stressrelief.datacontext.model.StressReliefAction;
import psychotherapy.stressrelief.datacontext.repository.StressReliefActionRepository;
import psychotherapy.stressrelief.features.patient.checkpatientexistence.CheckPatientExistenceHandler;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class CreateMeditationLogHandler {
    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;
    @Autowired
    private CheckPatientExistenceHandler patientExistenceHandler;
    @GrpcClient("grpc")
    ba.unsa.etf.pnwt.proto.LoggingServiceGrpc.LoggingServiceBlockingStub loggingServiceBlockingStub;

    public ResponseEntity<CreateMeditationLogResponse> handle(CreateMeditationLogRequest request) {
        var patientExists = patientExistenceHandler.handle(request.getPatientId());
        if (!patientExists) {
            return ResponseEntity.notFound().build();
        }
        StressReliefAction action = CreateMeditationLogMapper.INSTANCE.requestToEntity(request);
        action.setStartedAt(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC));
        Meditation savedMeditation = (Meditation) stressReliefActionRepository.save(action);
        LoggingRequest loggingRequest = LoggingRequest.newBuilder()
                .setServiceName("StressReliefService")
                .setControllerName("StressReliefActionController")
                .setActionUrl("/stressrelief/meditation")
                .setActionType("POST")
                .setActionResponse("SUCCESS")
                .build();
        loggingServiceBlockingStub.logRequest(loggingRequest);
        CreateMeditationLogResponse response = CreateMeditationLogMapper.INSTANCE.entityToResponse(savedMeditation);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
