package com.team6.chat_service.global.config.redis.dto;

import java.io.Serializable;
import lombok.Builder;

@Builder
public record ChatMessageBroadcastDto(
        String destination,
        Object payload
) implements Serializable {
    public static ChatMessageBroadcastDto from(String dest, Object payload) {
        return ChatMessageBroadcastDto.builder()
                .destination(dest)
                .payload(payload)
                .build();
    }
}
