package com.team6.chat_service.chat.ui.dto;

import com.team6.chat_service.chat.domain.ChatMessage;
import lombok.Builder;

@Builder
public record ChatMessageResponse(
        Long id,
        Long senderId,
        String senderName,
        String content,
        String messageType,
        String eventType,
        String createdAt,
        int unreadCount
) {
    public static ChatMessageResponse from(ChatMessage chatMessage, int unreadCount) {
        return ChatMessageResponse.builder()
                .id(chatMessage.getId())
                .senderId(chatMessage.getSenderId())
                .senderName(chatMessage.getSenderName())
                .content(chatMessage.getContent())
                .messageType(chatMessage.getMessageType().name())
                .eventType(chatMessage.getEventType().name())
                .createdAt(chatMessage.getCreatedAt().toString())
                .unreadCount(unreadCount)
                .build();
    }
}
