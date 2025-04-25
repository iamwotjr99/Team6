package com.team6.chat_service.chat.dto;

import com.team6.chat_service.chat.domain.ChatMessage;
import lombok.Builder;

@Builder
public record ChatMessageResponse(
        Long senderId,
        String senderName,
        String content,
        String type,
        String createdAt
) {
    public static ChatMessageResponse from(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .senderId(chatMessage.getSenderId())
                .senderName(chatMessage.getSenderName())
                .content(chatMessage.getContent())
                .type(chatMessage.getType().name())
                .createdAt(chatMessage.getCreatedAt().toString())
                .build();
    }
}
