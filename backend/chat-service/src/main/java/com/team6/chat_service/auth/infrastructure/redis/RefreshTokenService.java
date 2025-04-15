package com.team6.chat_service.auth.infrastructure.redis;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisTemplate<String, String> redisTemplate;

    public void save(Long userId, String refreshToken, long expiresIn) {
        redisTemplate.opsForValue()
                .set("refresh_token:" + userId, refreshToken, expiresIn, TimeUnit.MILLISECONDS);
    }

    public String get(Long userId) {
        return redisTemplate.opsForValue().get("refresh_token:" + userId);
    }

    public void delete(Long userId) {
        redisTemplate.delete("refresh_token:" + userId);
    }
}
