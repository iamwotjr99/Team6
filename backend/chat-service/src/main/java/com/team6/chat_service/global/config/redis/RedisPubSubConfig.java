package com.team6.chat_service.global.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
@RequiredArgsConstructor
public class RedisPubSubConfig {

    // Redis와의 연결을 생성하고 관리하는 객체
    private final RedisConnectionFactory redisConnectionFactory;

    // STOMP 메세지를 구독자에게 실제로 전달하는 역할
    private final SimpMessagingTemplate messagingTemplate;

    // Redis Pub/Sub에서 사용할 채널 이름 설정
    @Bean
    public ChannelTopic chatTopic() {
        return new ChannelTopic("chatroom");
    }

    // Redis가 메세지를 수신하면 listener에게 전달하는 컨테이너
    // "chatroom" 채널에 메시지가 들어오면 listenerAdapter가 동작
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(listenerAdapter, chatTopic());
        return container;
    }

    // RedisSubscriber - Redis에서 메시지를 받았을 때 처리하는 로직이 있는 클래스
    // MessageListenerAdapter는 RedisSubscriber의 onMessage() 메서드를 대신 호출해주는 어댑터
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisSubscriber redisSubscriber) {
        return new MessageListenerAdapter(redisSubscriber);
    }
}
