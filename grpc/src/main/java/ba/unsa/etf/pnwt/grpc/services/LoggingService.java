package ba.unsa.etf.pnwt.grpc.services;

import ba.unsa.etf.pnwt.grpc.entity.LoggingEntity;
import ba.unsa.etf.pnwt.grpc.mapper.LoggingMapper;
import ba.unsa.etf.pnwt.grpc.repository.LoggingRepository;
import ba.unsa.etf.pnwt.proto.LoggingRequest;
import ba.unsa.etf.pnwt.proto.LoggingResponse;
import ba.unsa.etf.pnwt.proto.LoggingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class LoggingService extends LoggingServiceGrpc.LoggingServiceImplBase {
    @Autowired protected LoggingRepository loggingRepository;

    @Override
    public void logRequest(LoggingRequest request, StreamObserver<LoggingResponse> responseObserver) {
        LoggingEntity loggingEntity = LoggingMapper.mapToEntity(request);
        loggingRepository.save(loggingEntity);

        LoggingResponse loggingResponse = LoggingResponse.newBuilder().setResponseMessage("Sucess").build();
        responseObserver.onNext(loggingResponse);
        responseObserver.onCompleted();
    }


}
