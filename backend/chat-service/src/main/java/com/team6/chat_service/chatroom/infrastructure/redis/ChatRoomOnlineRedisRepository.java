package com.team6.chat_service.chatroom.infrastructure.redis;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomOnlineRedisRepository {
    private final RedisTemplate<String, String> redisTemplate;

    // key: chatroom:online:{roomId}
    // value: userIds

    // key 만드는 함수
    private String getKey(Long roomId) {
        return "chatroom:online:" + roomId;
    }


    // 유저 접속 온라인 상태(채팅방 접속상태)
    public void addOnlineUser(Long roomId, Long userId) {
        redisTemplate.opsForSet().add(getKey(roomId), userId.toString());
    }

    // 유저 접속 오프라인 상태(채팅방 나감상태)
    public void removeOnlineUser(Long roomId, Long userId) {
        redisTemplate.opsForSet().remove(getKey(roomId), userId.toString());
    }

    // 현재 채팅방에 접속중인 유저 Id 집합
    public Set<String> getOnlineUserIds(Long roomId) {
        return redisTemplate.opsForSet().members(getKey(roomId));
    }

    // 현재 채팅방에 접속중인 유저 수
    public long countOnlineUsers(Long roomId) {
        return redisTemplate.opsForSet().size(getKey(roomId));
    }

}
