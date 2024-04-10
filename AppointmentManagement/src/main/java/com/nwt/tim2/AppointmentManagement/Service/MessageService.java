package com.nwt.tim2.AppointmentManagement.Service;

import com.nwt.tim2.AppointmentManagement.Dtos.Mapper.MessageMapper;
import com.nwt.tim2.AppointmentManagement.Dtos.MessageDto;
import com.nwt.tim2.AppointmentManagement.Dtos.PatientDto;
import com.nwt.tim2.AppointmentManagement.Exception.MessageNotFound;
import com.nwt.tim2.AppointmentManagement.Exception.UserNotFound;
import com.nwt.tim2.AppointmentManagement.Models.Message;
import com.nwt.tim2.AppointmentManagement.Models.Patient;
import com.nwt.tim2.AppointmentManagement.Repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
@Service
public class MessageService {

    private final MessageRepo messageRepository;
    private final PatientService patientService;
    private final MessageMapper messageMapper;
    @Autowired
    public MessageService(MessageRepo messageRepository,PatientService patientService,MessageMapper m) {
        this.messageRepository = messageRepository;
        this.patientService=patientService;
        messageMapper=m;
    }

    public List<MessageDto> getAllMessagesForPatientOrPsychologist(String patientId) {
        PatientDto patient = patientService.findById(patientId)
                .orElseThrow(() -> new UserNotFound("User not found."));
        List<Message> m=messageRepository.findByRecipientUserIdAndSenderUserIdOrRecipientUserIdAndSenderUserId(
                patient.getUserId(), patient.getSelectedPsychologistId(),
                patient.getSelectedPsychologistId(), patient.getUserId()
        );
        return m.stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }


    public MessageDto createMessage(@Valid MessageDto message, String patientId) {
        PatientDto patient = patientService.findById(patientId)
                .orElseThrow(() -> new UserNotFound("User not found."));

        if ((message.getSenderId().equals(patient.getUserId()) && message.getRecipientId().equals(patient.getSelectedPsychologistId())) ||
                (message.getRecipientId().equals(patient.getUserId()) && message.getSenderId().equals(patient.getSelectedPsychologistId()))) {
            Message messageEntity = messageMapper.fromDto(message);
            Message savedMessage = messageRepository.save(messageEntity);
            return messageMapper.toDto(savedMessage);
        }
        return null;
    }

    public boolean deleteMessage(MessageDto message) {
        Message existingMessage = messageRepository.findMessageBySenderAndRecipientAndContentAndOtherParams(
                message.getSenderId(), message.getRecipientId(), message.getContent());
        if (existingMessage != null) {
            messageRepository.delete(existingMessage);
            return true;
        } else {
            throw new MessageNotFound("Message with exact parameters not found.");
        }
    }
}
