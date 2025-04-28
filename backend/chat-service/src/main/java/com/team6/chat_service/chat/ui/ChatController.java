package com.team6.chat_service.chat.ui;

import com.team6.chat_service.chat.application.ChatMessageService;
import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chat.ui.dto.ChatMessageResponse;
import com.team6.chat_service.chat.ui.dto.ChatMessageSendRequest;
import java.util.List;
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
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chatroom/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, @Payload ChatMessageSendRequest message) {
        log.info("Sending message: {}", message);

        if (message.eventType().equals("ENTER")) {
            sendPreviousMessages(roomId);
        } else {
            sendNewMessage(roomId, message);
        }
    }

    private void sendPreviousMessages(Long roomId) {
        List<ChatMessage> messages = chatMessageService.getChatMessageInRoom(roomId);

        List<ChatMessageResponse> response = messages.stream()
                .map(ChatMessageResponse::from)
                .toList();

        messagingTemplate.convertAndSend("/sub/chatroom/" + roomId, response);
    }

    private void sendNewMessage(Long roomId, ChatMessageSendRequest message) {
        ChatMessage savedMessage = chatMessageService.createChatMessage(roomId, message);

        ChatMessageResponse response = ChatMessageResponse.from(savedMessage);

        messagingTemplate.convertAndSend("/sub/chatroom/" + roomId, response);
    }

}
