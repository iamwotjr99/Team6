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
    private ChatMessageType messageType;
    private ChatEventType eventType;
    private LocalDateTime createdAt;
    private int participantCount;

    public static ChatMessage createChatMessage(Long senderId, Long roomId, String senderName,
            String content, String messageType, String eventType, int participantCount) {
        return new ChatMessage(senderId, roomId, senderName,
                content, ChatMessageType.from(messageType), ChatEventType.from(eventType), participantCount);
    }

    public static ChatMessage createChatMessage(Long senderId, Long roomId, String senderName,
            String content, String messageType, String eventType) {
        return new ChatMessage(senderId, roomId, senderName,
                content, ChatMessageType.from(messageType), ChatEventType.from(eventType), 0);
    }

    private ChatMessage(Long senderId, Long roomId, String senderName, String content,
            ChatMessageType messageType, ChatEventType eventType, int participantCount) {
        this.senderId = senderId;
        this.roomId = roomId;
        this.senderName = senderName;
        this.content = content;
        this.messageType = messageType;
        this.eventType = eventType;
        this.createdAt = LocalDateTime.now();
        this.participantCount = participantCount;
    }
}
