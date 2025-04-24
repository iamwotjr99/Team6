package com.team6.chat_service.chatroom.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomUser {
    private Long id;
    private Long userId;
    private Long chatRoomId;
    private LocalDateTime joinedAt;

    public static ChatRoomUser join(Long userId, Long chatRoomId) {
        return new ChatRoomUser(userId, chatRoomId);
    }

    private ChatRoomUser(Long userId, Long chatRoomId) {
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.joinedAt = LocalDateTime.now();
    }
}
