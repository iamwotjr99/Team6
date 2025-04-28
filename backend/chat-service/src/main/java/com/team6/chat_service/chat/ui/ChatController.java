package com.team6.chat_service.chat.ui;

import com.team6.chat_service.chat.application.ChatMessageService;
import com.team6.chat_service.chat.domain.ChatEventType;
import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chat.ui.dto.ChatMessageResponse;
import com.team6.chat_service.chat.ui.dto.ChatMessageSendRequest;
import com.team6.chat_service.chatroom.application.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.service.GenericResponseService;
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
    private final ChatRoomService chatRoomService;

    @MessageMapping("/chatroom/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, @Payload ChatMessageSendRequest message) {
        log.info("Sending message: {}", message);

        ChatEventType eventType = ChatEventType.from(message.eventType());

        if (eventType == ChatEventType.ENTER) {
            handleEnter(roomId, message);
        } else {
            handleChatMessage(roomId, message);
        }
    }

    private void handleEnter(Long roomId, ChatMessageSendRequest message) {
        boolean alreadyEntered = chatRoomService.enterChatRoom(message.senderId(), roomId);

        if(!alreadyEntered) {
            ChatMessage enterMessage = ChatMessage.createChatMessage(
                    message.senderId(),
                    roomId,
                    message.senderName(),
                    message.senderName() + "님이 입장했습니다.",
                    "TEXT",
                    "ENTER"
            );

            ChatMessage savedEnterMessage = chatMessageService.createChatMessage(roomId,
                    ChatMessageSendRequest.from(enterMessage));

            ChatMessageResponse response = ChatMessageResponse.from(savedEnterMessage);

            messagingTemplate.convertAndSend("/sub/chatroom/" + roomId, response);
        }
    }

    private void handleChatMessage(Long roomId, ChatMessageSendRequest message) {
        ChatMessage sendMessage = chatMessageService.createChatMessage(roomId, message);

        ChatMessageResponse response = ChatMessageResponse.from(sendMessage);

        messagingTemplate.convertAndSend("/sub/chatroom/" + roomId, response);
    }

}
