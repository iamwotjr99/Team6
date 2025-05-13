package com.team6.chat_service.chatroom.infrastructure;

import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.chatroom.domain.entity.ChatRoomEntity;
import com.team6.chat_service.chatroom.domain.repository.ChatRoomRepository;
import com.team6.chat_service.chatroom.infrastructure.jpa.JpaChatRoomRepository;
import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;
import com.team6.chat_service.user.domain.User;
import com.team6.chat_service.user.domain.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    public Optional<ChatRoom> findById(Long roomId) {
        return jpaChatRoomRepository.findById(roomId)
                .map(ChatRoomEntity::toChatRoom);
    }

    @Override
    public void updateLastMessage(Long roomId, String lastMessage, LocalDateTime lastMessageAt) {
        ChatRoomEntity chatRoomEntity = jpaChatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));

        ChatRoom chatRoom = chatRoomEntity.toChatRoom();
        chatRoom.updateLastMessage(lastMessage, lastMessageAt);

        chatRoomEntity.updateFromDomain(chatRoom);
    }

    @Override
    public List<ChatRoom> findJoinedByUserId(Long userId) {
        return jpaChatRoomRepository.findJoinedByUserId(userId)
                .stream()
                .map(ChatRoomEntity::toChatRoom)
                .toList();
    }

    @Override
    public List<ChatRoom> findNotJoinedByUserId(Long userId) {
        return jpaChatRoomRepository.findNotJoinedByUserId(userId)
                .stream()
                .map(ChatRoomEntity::toChatRoom)
                .toList();
    }

    @Override
    public int countByCreatedUserId(Long userId) {
        return jpaChatRoomRepository.countByCreator_Id(userId);
    }

    @Override
    public void updateLastMessage(ChatRoom chatRoom) {
        ChatRoomEntity chatRoomEntity = jpaChatRoomRepository.findById(chatRoom.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));

        chatRoomEntity.updateFromDomain(chatRoom);
        jpaChatRoomRepository.save(chatRoomEntity);
    }
}
