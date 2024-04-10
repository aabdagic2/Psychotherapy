package com.nwt.tim2.AppointmentManagement.Repos;


import com.nwt.tim2.AppointmentManagement.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message,String> {
    List<Message> findByRecipientUserIdAndSenderUserIdOrRecipientUserIdAndSenderUserId(
            String recipientUserId1, String senderUserId1,
            String recipientUserId2, String senderUserId2
    );
    @Query("SELECT m FROM Message m WHERE m.sender = :sender AND m.recipient = :recipient AND m.content = :content") // add other parameters
    Message findMessageBySenderAndRecipientAndContentAndOtherParams(
            String sender, String recipient, String content
    );

}