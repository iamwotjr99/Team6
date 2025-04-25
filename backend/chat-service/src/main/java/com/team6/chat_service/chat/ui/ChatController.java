package com.team6.chat_service.chat.ui;

import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chat.domain.ChatMessageType;
import com.team6.chat_service.chat.dto.ChatMessageResponse;
import com.team6.chat_service.chat.dto.ChatMessageSendRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chatroom/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, @Payload ChatMessageSendRequest message) {
        log.info("Sending message: {}", message);
        ChatMessage chatMessage = ChatMessage.createChatMessage(
                message.senderId(),
                roomId,
                message.senderName(),
                message.content(),
                ChatMessageType.from(message.type())
        );

        ChatMessageResponse response = ChatMessageResponse.from(chatMessage);
        messagingTemplate.convertAndSend("/sub/chatroom/" + roomId, response);
    }

}
