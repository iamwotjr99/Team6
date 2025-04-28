package com.team6.chat_service.chatroom.domain.repository;

import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.chatroom.domain.ChatRoomUser;
import com.team6.chat_service.user.domain.User;
import java.util.List;
import java.util.Optional;

public interface ChatRoomUserRepository {
    void save(ChatRoom chatRoom, User user);
    List<Long> findChatRoomIdsByUserId(Long userId);
    boolean existsByUserIdAndRoomId(Long userId, Long roomId);
}
