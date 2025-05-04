package com.team6.chat_service.chat.ui.dto;

import lombok.Builder;

@Builder
public record ChatMessageReadUpdateDto(
        Long messageId,
        int unreadCount
) {
    public static ChatMessageReadUpdateDto from(Long messageId, int unreadCount) {
        return ChatMessageReadUpdateDto.builder()
                .messageId(messageId)
                .unreadCount(unreadCount)
                .build();
    }
}
