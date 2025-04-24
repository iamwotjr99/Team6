package com.team6.chat_service.chatroom.application.dto;

public record CreateChatRoomDto(
        String roomTitle,
        Long userId
) {

}
