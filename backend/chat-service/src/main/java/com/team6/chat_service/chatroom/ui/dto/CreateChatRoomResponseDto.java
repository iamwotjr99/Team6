package com.team6.chat_service.chatroom.ui.dto;

import com.team6.chat_service.chatroom.domain.ChatRoom;

public record CreateChatRoomResponseDto(
        Long id,
        String title
) {
    public static CreateChatRoomResponseDto from(ChatRoom chatRoom) {
        return new CreateChatRoomResponseDto(
                chatRoom.getId(),
                chatRoom.getTitle().getValue()
        );
    }
}
