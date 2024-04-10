package com.AppointmentManagement;

import com.nwt.tim2.AppointmentManagement.Controllers.MessageController;
import com.nwt.tim2.AppointmentManagement.Dtos.Mapper.MessageMapper;
import com.nwt.tim2.AppointmentManagement.Dtos.MessageDto;
import com.nwt.tim2.AppointmentManagement.Dtos.PatientDto;
import com.nwt.tim2.AppointmentManagement.Exception.MessageNotFound;
import com.nwt.tim2.AppointmentManagement.Exception.UserNotFound;
import com.nwt.tim2.AppointmentManagement.Models.Message;
import com.nwt.tim2.AppointmentManagement.Repos.MessageRepo;
import com.nwt.tim2.AppointmentManagement.Service.MessageService;
import com.nwt.tim2.AppointmentManagement.Service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MessageController.class)
class MessageTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Mock
    private MessageRepo messageRepository;

    @Mock
    private PatientService patientService;

    @Mock
    private MessageMapper messageMapper;


    @Test
    void getAllMessagesForPatientOrPsychologist() throws Exception {
        List<MessageDto> messages = new ArrayList<>();
        when(messageService.getAllMessagesForPatientOrPsychologist("patientId")).thenReturn(messages);

        this.mockMvc.perform(get("/messages/patient/patientId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createMessage() throws Exception {
        MessageDto message = new MessageDto();
        when(patientService.findById(any())).thenReturn(Optional.of(new PatientDto()));
        when(messageRepository.save(any())).thenReturn(new Message());
        when(messageMapper.toDto(any())).thenReturn(message);

        this.mockMvc.perform(post("/messages/create/patientId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"senderId\":\"senderId\",\"recipientId\":\"recipientId\",\"content\":\"content\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteMessage() throws Exception {
        this.mockMvc.perform(delete("/messages/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"senderId\":\"senderId\",\"recipientId\":\"recipientId\",\"content\":\"content\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Message deleted successfully")));
    }

    @Test
    void getAllMessagesForPatientOrPsychologist_ValidPatientId_ReturnsMessages() {
        List<Message> messages = new ArrayList<>();
        when(messageRepository.findByRecipientUserIdAndSenderUserIdOrRecipientUserIdAndSenderUserId(any(), any(), any(), any())).thenReturn(messages);
        List<MessageDto> expected = new ArrayList<>();
        when(messageMapper.toDto(any())).thenReturn(new MessageDto());

        List<MessageDto> actual = messageService.getAllMessagesForPatientOrPsychologist("patientId");

        assertEquals(expected, actual);
    }

    @Test
    void createMessage_ValidData_ReturnsMessageDto() {
        MessageDto message = new MessageDto();
        when(patientService.findById(any())).thenReturn(Optional.of(new PatientDto()));
        when(messageRepository.save(any())).thenReturn(new Message());
        when(messageMapper.toDto(any())).thenReturn(message);

        MessageDto actual = messageService.createMessage(new MessageDto(), "patientId");

        assertEquals(message, actual);
    }

    @Test
    void createMessage_InvalidData_ThrowsUserNotFound() {
        when(patientService.findById(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> messageService.createMessage(new MessageDto(), "invalidPatientId"));
    }

    @Test
    void deleteMessage_ExistingMessage_DeletesMessage() {
        when(messageRepository.findMessageBySenderAndRecipientAndContentAndOtherParams(any(), any(), any())).thenReturn(new Message());

        messageService.deleteMessage(new MessageDto());

        verify(messageRepository, times(1)).delete(any());
    }

    @Test
    void deleteMessage_NonExistingMessage_ThrowsMessageNotFound() {
        when(messageRepository.findMessageBySenderAndRecipientAndContentAndOtherParams(any(), any(), any())).thenReturn(null);

        assertThrows(MessageNotFound.class, () -> messageService.deleteMessage(new MessageDto()));
    }
}
