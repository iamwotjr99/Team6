package com.team6.chat_service.chatroom.infrastructure;

import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.chatroom.domain.ChatRoomUser;
import com.team6.chat_service.chatroom.domain.entity.ChatRoomEntity;
import com.team6.chat_service.chatroom.domain.entity.ChatRoomUserEntity;
import com.team6.chat_service.chatroom.domain.repository.ChatRoomUserRepository;
import com.team6.chat_service.chatroom.infrastructure.jpa.JpaChatRoomRepository;
import com.team6.chat_service.chatroom.infrastructure.jpa.JpaChatRoomUserRepository;
import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;
import com.team6.chat_service.user.domain.User;
import com.team6.chat_service.user.domain.entity.UserEntity;
import com.team6.chat_service.user.infrastructure.jpa.JpaUserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomUserRepositoryImpl implements ChatRoomUserRepository {

    private final JpaChatRoomUserRepository jpaChatRoomUserRepository;
    private final JpaChatRoomRepository jpaChatRoomRepository;
    private final JpaUserRepository jpaUserRepository;

    @Override
    public void save(ChatRoom chatRoom, User user) {
        UserEntity userEntity = jpaUserRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        ChatRoomEntity chatRoomEntity = jpaChatRoomRepository.findById(chatRoom.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));

        ChatRoomUserEntity chatRoomUserEntity = new ChatRoomUserEntity(userEntity, chatRoomEntity);

        jpaChatRoomUserRepository.save(chatRoomUserEntity);
    }

    @Override
    public List<Long> findChatRoomIdsByUserId(Long userId) {
        return jpaChatRoomUserRepository.findChatRoomIdsByUserId(userId);
    }

    @Override
    public Optional<ChatRoomUser> findChatRoomUserByUserIdAndRoomId(Long userId, Long roomId) {
        return jpaChatRoomUserRepository.findByUserEntity_IdAndChatRoomEntity_Id(userId, roomId)
                .map(ChatRoomUserEntity::toChatRoomUser);
    }

    @Override
    public boolean existsByUserIdAndRoomId(Long userId, Long roomId) {
        return jpaChatRoomUserRepository.existsByUserEntity_IdAndChatRoomEntity_Id(userId, roomId);
    }

    @Override
    public int deleteByUserIdAndRoomId(Long userId, Long roomId) {
        return jpaChatRoomUserRepository.deleteByUserEntity_IdAndChatRoomEntity_Id(userId, roomId);
    }

    @Override
    public int countByRoomId(Long roomId) {
        return jpaChatRoomUserRepository.countByChatRoomEntity_Id(roomId);
    }

    @Override
    public int countByUserId(Long userId) {
        return jpaChatRoomUserRepository.countByUserEntity_Id(userId);
    }

    @Override
    public Set<Long> findUserIdsByRoomId(Long roomId) {
        return jpaChatRoomUserRepository.findUserIdsByRoomId(roomId);
    }

    @Override
    public List<ChatRoomUser> findByRoomId(Long roomId) {
        return jpaChatRoomUserRepository.findByChatRoomEntity_Id(roomId)
                .stream()
                .map(ChatRoomUserEntity::toChatRoomUser)
                .toList();
    }


}
