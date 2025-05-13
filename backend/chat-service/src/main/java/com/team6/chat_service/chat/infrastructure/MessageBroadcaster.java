package com.team6.chat_service.chat.infrastructure;

public interface MessageBroadcaster {
    void broadcast(String topic, Object payload);
}
