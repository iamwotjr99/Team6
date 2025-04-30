package com.team6.chat_service.chat.infrastructure;

import com.team6.chat_service.chat.domain.ChatMessageRead;
import com.team6.chat_service.chat.domain.entity.ChatMessageEntity;
import com.team6.chat_service.chat.domain.entity.ChatMessageReadEntity;
import com.team6.chat_service.chat.domain.repository.ChatMessageReadRepository;
import com.team6.chat_service.chat.infrastructure.jpa.JpaChatMessageReadRepository;
import com.team6.chat_service.chat.infrastructure.jpa.JpaChatMessageRepository;
import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;
import com.team6.chat_service.user.domain.entity.UserEntity;
import com.team6.chat_service.user.infrastructure.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatMessageReadRepositoryImpl implements ChatMessageReadRepository {

    private final JpaChatMessageReadRepository jpaChatMessageReadRepository;
    private final JpaChatMessageRepository jpaChatMessageRepository;
    private final JpaUserRepository jpaUserRepository;

    @Override
    public void save(Long chatMessageId, Long userId) {
        ChatMessageRead messageRead = ChatMessageRead.createMessageRead(chatMessageId, userId);

        ChatMessageEntity chatMessageEntity = jpaChatMessageRepository.findById(chatMessageId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHAT_MESSAGE_NOT_FOUND));

        UserEntity userEntity = jpaUserRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ChatMessageReadEntity chatMessageReadEntity = new ChatMessageReadEntity(messageRead,
                chatMessageEntity, userEntity);

        if(!jpaChatMessageReadRepository.existsByChatMessageEntity_IdAndUserEntity_Id(chatMessageId, userId)) {
            jpaChatMessageReadRepository.save(chatMessageReadEntity);
        }
    }

    @Override
    public int countUnreadMessagesByRoomIdAndUserId(Long roomId, Long userId) {
        return jpaChatMessageReadRepository.countUnreadMessagesByRoomIdAndUserId(roomId, userId);
    }
}
