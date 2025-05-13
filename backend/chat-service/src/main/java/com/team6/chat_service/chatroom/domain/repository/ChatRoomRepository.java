package com.team6.chat_service.chatroom.domain.repository;

import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {
    ChatRoom save(ChatRoom chatRoom, User user);

    Optional<ChatRoom> findById(Long roomId);

    void updateLastMessage(Long roomId, String lastMessage, LocalDateTime lastMessageAt);

    List<ChatRoom> findJoinedByUserId(Long userId);

    List<ChatRoom> findNotJoinedByUserId(Long userId);

    int countByCreatedUserId(Long userId);

    void updateLastMessage(ChatRoom chatRoom);
}
