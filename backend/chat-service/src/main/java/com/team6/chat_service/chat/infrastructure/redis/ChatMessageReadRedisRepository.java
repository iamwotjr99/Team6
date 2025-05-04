package com.team6.chat_service.chat.infrastructure.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatMessageReadRedisRepository {
    private final RedisTemplate<String, String> redisTemplate;

    // key: message:read:{chatId}
    // value: userIds

    // key 만드는 함수
    private String getKey(Long chatId) {
        return "message:read:" + chatId;
    }

    // 메세지를 읽은 유저 추가
    public void markAsRead(Long chatId, Long userId) {
        redisTemplate.opsForSet().add(getKey(chatId), String.valueOf(userId));
        redisTemplate.expire(getKey(chatId), 7, TimeUnit.DAYS);
    }

    // 유저가 이 메세지를 읽었는지 확인
    public boolean isRead(Long chatId, Long userId) {
        return Boolean.TRUE.equals(
                redisTemplate.opsForSet().isMember(getKey(chatId), String.valueOf(userId))
        );
    }

    // 이 메시지를 읽은 유저 수 (안읽은 유저수 = 전체 유저수 - 읽은 유저 수)
    public long countReadUsers(Long chatId) {
        return redisTemplate.opsForSet().size(getKey(chatId));
    }

    // 이 메세지를 읽은 유저의 id 집합을 리턴
    public Set<String> getReadUserIds(Long chatId) {
        return redisTemplate.opsForSet().members(getKey(chatId));
    }

    public void deleteKey(Long chatId) {
        redisTemplate.delete(getKey(chatId));
    }
}
