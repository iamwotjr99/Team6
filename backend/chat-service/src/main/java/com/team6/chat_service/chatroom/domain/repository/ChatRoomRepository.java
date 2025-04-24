package com.team6.chat_service.chatroom.domain.repository;

import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.user.domain.User;
import java.util.List;

public interface ChatRoomRepository {
    ChatRoom save(ChatRoom chatRoom, User user);
    List<ChatRoom> findChatRoomsByCreatorId(Long userId);
    List<ChatRoom> findChatRoomsByIds(List<Long> chatRoomIds);
}
