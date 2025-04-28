package com.team6.chat_service.chat.domain.entity;

import com.team6.chat_service.chat.domain.ChatEventType;
import jakarta.persistence.AttributeConverter;

public class ChatEventTypeConverter implements AttributeConverter<ChatEventType, String> {

    @Override
    public String convertToDatabaseColumn(ChatEventType attribute) {
        return attribute.name();
    }

    @Override
    public ChatEventType convertToEntityAttribute(String dbData) {
        return ChatEventType.from(dbData);
    }
}
