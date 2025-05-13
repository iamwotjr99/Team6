package com.team6.chat_service.global.config.redis;

import com.team6.chat_service.chat.infrastructure.MessageBroadcaster;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class RedisPublisher implements MessageBroadcaster {
    // 채널명(String) : 메세지(Object)
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisPublisher(@Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

//    // 실제 메시지를 Redis의 특정 topic(채널)에 발행(publish)하는 메서드
//    public void publish(ChannelTopic topic, Object message) {
//
//        // 해당 채널에 메시지를 발행
//        redisTemplate.convertAndSend(topic.getTopic(), message);
//    }

    @Override
    public void broadcast(String topic, Object payload) {
        redisTemplate.convertAndSend(topic, payload);
    }
}
