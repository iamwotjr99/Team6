package com.team6.chat_service.chatroom.domain.repository;

import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.user.domain.User;
import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {
    ChatRoom save(ChatRoom chatRoom, User user);
    Optional<ChatRoom> findById(Long roomId);
    List<ChatRoom> findChatRoomsByCreatorId(Long userId);
    List<ChatRoom> findChatRoomsByIds(List<Long> chatRoomIds);
}
