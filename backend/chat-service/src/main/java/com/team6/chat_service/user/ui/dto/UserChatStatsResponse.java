package com.team6.chat_service.user.ui.dto;

import lombok.Builder;

@Builder
public record UserChatStatsResponse(
        int createdRoomCount,
        int joinedRoomCount,
        int sendMessageCount
) {
    public static UserChatStatsResponse from(int createdRoomCount, int joinedRoomCount, int sendMessageCount) {
        return UserChatStatsResponse.builder()
                .createdRoomCount(createdRoomCount)
                .joinedRoomCount(joinedRoomCount)
                .sendMessageCount(sendMessageCount)
                .build();
    }
}
