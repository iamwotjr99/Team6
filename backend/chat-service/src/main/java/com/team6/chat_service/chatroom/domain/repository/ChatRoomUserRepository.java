package com.team6.chat_service.chatroom.domain.repository;

import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.chatroom.domain.ChatRoomUser;
import com.team6.chat_service.user.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChatRoomUserRepository {
    void save(ChatRoom chatRoom, User user);

    List<Long> findChatRoomIdsByUserId(Long userId);

    Optional<ChatRoomUser> findChatRoomUserByUserIdAndRoomId(Long userId, Long roomId);

    boolean existsByUserIdAndRoomId(Long userId, Long roomId);

    int deleteByUserIdAndRoomId(Long userId, Long roomId);

    int countByRoomId(Long roomId);

    int countByUserId(Long userId);

    Set<Long> findUserIdsByRoomId(Long roomId);

    List<ChatRoomUser> findByRoomId(Long roomId);
}
