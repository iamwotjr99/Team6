package com.team6.chat_service.chat.ui.dto;

public record ChatMessageSendRequest(
        Long senderId,
        String senderName,
        String content,
        String messageType, // TEXT, PICTURE, VIDEO
        String eventType // ENTER, LEAVE, NONE
) {
}
