package com.nwt.tim2.AppointmentManagement.Service;

import com.nwt.tim2.AppointmentManagement.Dtos.Mapper.PsychologistMapper;
import com.nwt.tim2.AppointmentManagement.Dtos.PsychologistDto;
import com.nwt.tim2.AppointmentManagement.Exception.IdNotUUID;
import com.nwt.tim2.AppointmentManagement.Exception.UserNotFound;
import com.nwt.tim2.AppointmentManagement.Models.Psychologist;
import com.nwt.tim2.AppointmentManagement.Repos.PsychologistRepo;
import com.nwt.tim2.AppointmentManagement.Responses.UserPsychologistResponse;
import jakarta.transaction.Transactional;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PsychologistService {
//    @GrpcClient("logging")
//    ba.unsa.etf.pnwt.proto.LoggingServiceGrpc.LoggingServiceBlockingStub loggingServiceBlockingStub;
    private final PsychologistRepo psychologistRepository;
    private final PsychologistMapper psychologistMapper;
    @Autowired
    public PsychologistService(PsychologistRepo psychologistRepository,PsychologistMapper p) {
        this.psychologistRepository = psychologistRepository;
        this.psychologistMapper=p;
    }

    public PsychologistDto savePsychologist(@Valid PsychologistDto psychologist) {
        if (!isValidUUID(psychologist.getUserId())) {

            throw new IdNotUUID("Invalid userId format");
        }
        Psychologist p=psychologistMapper.fromDto(psychologist);
        psychologistRepository.save(p);
        return psychologistMapper.toDto(p);
    }

    public List<PsychologistDto> getAllPsychologists() {
        List<Psychologist>list= psychologistRepository.findAll();
        return list.stream()
                .map(psychologistMapper::toDto)
                .collect(Collectors.toList());
    }

    public PsychologistDto getPsychologistById(String id) {
        if (!isValidUUID(id)) {
            throw new IdNotUUID("Invalid userId format");
        }
       Psychologist p=psychologistRepository.findById(id)
                .orElseThrow(() -> new UserNotFound("Psychologist not found."));
        return psychologistMapper.toDto(p);
    }

    @Transactional
    public void deletePsychologist(String id) {
        if (!isValidUUID(id)) {
            throw new IdNotUUID("Invalid userId format");
        }
        Optional<Psychologist> psychologistOptional = psychologistRepository.findById(id.toString());
        if (psychologistOptional.isPresent()) {
            psychologistRepository.deleteById(String.valueOf(id));
        } else {
            throw new UserNotFound("Psychologist not found");
        }
    }
    private boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
