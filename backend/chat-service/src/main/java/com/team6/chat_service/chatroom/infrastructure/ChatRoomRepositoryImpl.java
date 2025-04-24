package com.team6.chat_service.chatroom.infrastructure;

import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.chatroom.domain.entity.ChatRoomEntity;
import com.team6.chat_service.chatroom.domain.repository.ChatRoomRepository;
import com.team6.chat_service.chatroom.infrastructure.jpa.JpaChatRoomRepository;
import com.team6.chat_service.user.domain.User;
import com.team6.chat_service.user.domain.entity.UserEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final JpaChatRoomRepository jpaChatRoomRepository;

    @Override
    public ChatRoom save(ChatRoom chatRoom, User user) {
        UserEntity creatorEntity = new UserEntity(user);
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity(chatRoom, creatorEntity);
        ChatRoomEntity result = jpaChatRoomRepository.save(chatRoomEntity);
        return result.toChatRoom();
    }

    @Override
    public List<ChatRoom> findChatRoomsByCreatorId(Long userId) {
        return jpaChatRoomRepository.findByCreator_Id(userId)
                .stream()
                .map(ChatRoomEntity::toChatRoom)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatRoom> findChatRoomsByIds(List<Long> chatRoomIds) {
        return jpaChatRoomRepository.findByIdIn(chatRoomIds)
                .stream()
                .map(ChatRoomEntity::toChatRoom)
                .collect(Collectors.toList());
    }
}
