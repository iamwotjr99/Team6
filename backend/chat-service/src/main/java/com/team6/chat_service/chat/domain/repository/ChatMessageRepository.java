package com.team6.chat_service.chat.domain.repository;

import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage chatMessage, User user, ChatRoom chatRoom);

    List<ChatMessage> findByRoomId(Long roomId);

    List<ChatMessage> findByAfterJoinedAt(Long roomId, LocalDateTime joinedAt);

    int countBySenderId(Long userId);

}
