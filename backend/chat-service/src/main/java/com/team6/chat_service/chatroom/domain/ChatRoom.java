package com.team6.chat_service.chatroom.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    private Long id;
    private Long userId;
    private ChatRoomTitle title;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
    private LocalDateTime createdAt;

    public static ChatRoom createChatRoom(Long userId, ChatRoomTitle title) {
        return new ChatRoom(null, userId, title);
    }

    private ChatRoom(Long id, Long userId, ChatRoomTitle title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.lastMessage = null;
        this.lastMessageAt = null;
        this.createdAt = LocalDateTime.now();
    }

    public void updateLastMessage(String lastMessage, LocalDateTime lastMessageAt) {
        this.lastMessage = lastMessage;
        this.lastMessageAt = lastMessageAt;
    }
}
