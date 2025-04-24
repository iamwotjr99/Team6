package com.team6.chat_service.chatroom.ui.dto;

import com.team6.chat_service.chatroom.domain.ChatRoom;
import java.time.LocalDateTime;

public record GetChatRoomsResponseDto(
        Long id,
        String title,
        String lastMessage,
        LocalDateTime lastMessageAt
) {

    public static GetChatRoomsResponseDto from(ChatRoom chatRoom) {
        return new GetChatRoomsResponseDto(
                chatRoom.getId(),
                chatRoom.getTitle().getValue(),
                chatRoom.getLastMessage(),
                chatRoom.getLastMessageAt()
        );
    }
}
