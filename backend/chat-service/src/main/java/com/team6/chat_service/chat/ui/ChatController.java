package com.team6.chat_service.chat.ui;

import com.team6.chat_service.chat.dto.ChatMessage;
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
    public void sendMessage(@DestinationVariable String roomId, @Payload ChatMessage message) {
        log.info("Sending message: {}", message);
        messagingTemplate.convertAndSend("/sub/chatroom/" + roomId, message);
    }

}
