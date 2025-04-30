package com.team6.chat_service.chat.infrastructure;

import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chat.domain.entity.ChatMessageEntity;
import com.team6.chat_service.chat.domain.repository.ChatMessageRepository;
import com.team6.chat_service.chat.infrastructure.jpa.JpaChatMessageRepository;
import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.chatroom.domain.entity.ChatRoomEntity;
import com.team6.chat_service.chatroom.infrastructure.jpa.JpaChatRoomRepository;
import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;
import com.team6.chat_service.user.domain.User;
import com.team6.chat_service.user.domain.entity.UserEntity;
import com.team6.chat_service.user.infrastructure.jpa.JpaUserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final JpaChatMessageRepository jpaChatMessageRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaChatRoomRepository jpaChatRoomRepository;

    @Override
    public ChatMessage save(ChatMessage chatMessage, User user, ChatRoom chatRoom) {
        UserEntity userEntity = jpaUserRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        ChatRoomEntity chatRoomEntity = jpaChatRoomRepository.findById(chatRoom.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));

        ChatMessageEntity chatMessageEntity = new ChatMessageEntity(chatMessage, userEntity, chatRoomEntity);

        ChatMessageEntity result = jpaChatMessageRepository.save(chatMessageEntity);

        return result.toChatMessage();
    }

    @Override
    public List<ChatMessage> findByRoomId(Long roomId) {
        List<ChatMessageEntity> chatMessageEntities = jpaChatMessageRepository.findByChatRoomEntity_Id(
                roomId);

        return chatMessageEntities.stream()
                .map(ChatMessageEntity::toChatMessage)
                .toList();
    }

    @Override
    public List<ChatMessage> findByAfterJoinedAt(Long roomId, LocalDateTime joinedAt) {
        List<ChatMessageEntity> chatMessageEntities = jpaChatMessageRepository.findByAfterJoinedAt(
                roomId, joinedAt);

        return chatMessageEntities.stream()
                .map(ChatMessageEntity::toChatMessage)
                .toList();
    }
}
