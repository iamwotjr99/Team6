package com.team6.chat_service.chat.ui.dto;

import lombok.Builder;

@Builder
public record UnreadSummaryDto(
        Long roomId,
        String preview,
        String createdAt,
        Long unreadCount
) {
    public static UnreadSummaryDto from(Long roomId, String preview, String createdAt, Long unreadCount) {
        return UnreadSummaryDto.builder()
                .roomId(roomId)
                .preview(preview)
                .createdAt(createdAt)
                .unreadCount(unreadCount)
                .build();
    }
}
