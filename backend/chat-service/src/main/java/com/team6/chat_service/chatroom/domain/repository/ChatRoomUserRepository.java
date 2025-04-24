package com.team6.chat_service.chatroom.domain.repository;

import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.chatroom.domain.ChatRoomUser;
import com.team6.chat_service.user.domain.User;
import java.util.List;

public interface ChatRoomUserRepository {
    void save(ChatRoom chatRoom, User user);
    List<ChatRoomUser> findChatRoomByUserId(Long userId);
    List<Long> findChatRoomIdsByUserId(Long userId);
}
