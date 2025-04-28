package com.team6.chat_service.chat.ui.dto;

import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chat.domain.ChatMessageType;
import lombok.Builder;

@Builder
public record ChatMessageSendRequest(
        Long senderId,
        String senderName,
        String content,
        String messageType, // TEXT, PICTURE, VIDEO
        String eventType // ENTER, LEAVE, NONE
) {

    public static ChatMessageSendRequest from(ChatMessage chatMessage) {
        return ChatMessageSendRequest.builder()
                .senderId(chatMessage.getSenderId())
                .senderName(chatMessage.getSenderName())
                .content(chatMessage.getContent())
                .messageType(chatMessage.getMessageType().name())
                .eventType(chatMessage.getEventType().name())
                .build();
    }
}
