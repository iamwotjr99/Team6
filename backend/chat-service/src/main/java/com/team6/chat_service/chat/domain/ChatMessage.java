package com.team6.chat_service.chat.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private Long id;
    private Long senderId;
    private Long roomId;
    private String senderName;
    private String content;
    private ChatMessageType type;
    private LocalDateTime createdAt;

    public static ChatMessage createChatMessage(Long senderId, Long roomId, String senderName,
            String content, ChatMessageType type) {
        return new ChatMessage(senderId, roomId, senderName, content, type);
    }

    private ChatMessage(Long senderId, Long roomId, String senderName, String content,
            ChatMessageType type) {
        this.senderId = senderId;
        this.roomId = roomId;
        this.senderName = senderName;
        this.content = content;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }
}
