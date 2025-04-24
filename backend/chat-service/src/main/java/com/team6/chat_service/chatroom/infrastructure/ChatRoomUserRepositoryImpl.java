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
import java.util.stream.Collectors;
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
    public List<ChatRoomUser> findChatRoomByUserId(Long userId) {
        return jpaChatRoomUserRepository.findByUserEntity_Id(userId)
                .stream()
                .map(ChatRoomUserEntity::toChatRoomUser)
                .collect(Collectors.toList());
    }
}
