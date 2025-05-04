package com.team6.chat_service.chat.ui.dto;

import lombok.Builder;

@Builder
public record UnreadSummaryDto(
        Long roomId,
        Long unreadCount
) {
    public static UnreadSummaryDto from(Long roomId, Long unreadCount) {
        return UnreadSummaryDto.builder()
                .roomId(roomId)
                .unreadCount(unreadCount)
                .build();
    }
}
