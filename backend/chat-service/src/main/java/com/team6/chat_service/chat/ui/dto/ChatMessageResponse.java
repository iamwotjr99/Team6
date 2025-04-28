package com.team6.chat_service.chat.ui.dto;

import com.team6.chat_service.chat.domain.ChatMessage;
import lombok.Builder;

@Builder
public record ChatMessageResponse(
        Long senderId,
        String senderName,
        String content,
        String messageType,
        String eventType,
        String createdAt
) {
    public static ChatMessageResponse from(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .senderId(chatMessage.getSenderId())
                .senderName(chatMessage.getSenderName())
                .content(chatMessage.getContent())
                .messageType(chatMessage.getMessageType().name())
                .eventType(chatMessage.getEventType().name())
                .createdAt(chatMessage.getCreatedAt().toString())
                .build();
    }
}
