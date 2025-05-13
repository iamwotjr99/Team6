package com.team6.chat_service.global.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team6.chat_service.global.config.redis.dto.ChatMessageBroadcastDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
// Redis 메시지를 수신하는 역할
public class RedisSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper redisObjectMapper; // Redis에서 받은 JSON 메세지를 자바 객체로 변환

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {


            String body = new String(message.getBody()); // Redis 메세지의 body에는 byte[]로 오기 때문에 문자열로 바꿈

            // 메시지 body를 JSON ChatMessageBroadcastDto 객체로 변환
            ChatMessageBroadcastDto dto = redisObjectMapper.readValue(body,
                    ChatMessageBroadcastDto.class);

            System.out.println("📨 RedisSubscriber 받은 메시지: " + body);
            System.out.println("📨 역직렬화된 DTO: " + dto);
            System.out.println("📨 payload 내용: " + dto.payload());

            // 변환한 메세지를 실제 STOMP 클라이언트(프론트)에게 전송
            messagingTemplate.convertAndSend(dto.destination(), dto.payload());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
