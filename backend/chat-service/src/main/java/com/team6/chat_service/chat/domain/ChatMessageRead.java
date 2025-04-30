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
public class ChatMessageRead {
    private Long id;
    private Long chatMessageId;
    private Long userId;
    private LocalDateTime readAt;

    public static ChatMessageRead createMessageRead(Long chatMessageId, Long userId) {
        return new ChatMessageRead(chatMessageId, userId);
    }

    private ChatMessageRead(Long chatMessageId, Long userId) {
        this.chatMessageId = chatMessageId;
        this.userId = userId;
        this.readAt = LocalDateTime.now();
    }
}
