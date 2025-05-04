package com.team6.chat_service.chat.application.redis;

import com.team6.chat_service.chat.application.ChatMessageService;
import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chat.infrastructure.redis.ChatMessageReadRedisRepository;
import com.team6.chat_service.chat.ui.dto.ChatMessageSendRequest;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageReadRedisService {
    private final ChatMessageReadRedisRepository chatMessageReadRedisRepository;

    // 메세지 읽음 처리
    public void markAsRead(Long chatId, Long userId) {
        chatMessageReadRedisRepository.markAsRead(chatId, userId);
    }

    // 유저가 이 메세지를 읽었는지 확인
    public boolean isRead(Long chatId, Long userId) {
        return chatMessageReadRedisRepository.isRead(chatId, userId);
    }

    // 메시지 읽은 유저 수
    public long getReadUserCount(Long chatId) {
        return chatMessageReadRedisRepository.countReadUsers(chatId);
    }

    // 메세지 읽은 유저 ID 집합
    public Set<String> getReadUserIds(Long chatId) {
        return chatMessageReadRedisRepository.getReadUserIds(chatId);
    }

    // 메시지 읽음 상태 삭제
    public void deleteReadStatus(Long chatId) {
        chatMessageReadRedisRepository.deleteKey(chatId);
    }
}
