package com.team6.chat_service.chat.dto;

public record ChatMessageSendRequest(
        Long senderId,
        String senderName,
        String content,
        String type // TEXT, PICTURE, VIDEO
) {
}
