package com.team6.chat_service.chat.domain.entity;

import com.team6.chat_service.chat.domain.ChatMessageType;
import jakarta.persistence.AttributeConverter;

public class ChatMessageTypeConverter implements AttributeConverter<ChatMessageType, String> {

    @Override
    public String convertToDatabaseColumn(ChatMessageType attribute) {
        return attribute.name();
    }

    @Override
    public ChatMessageType convertToEntityAttribute(String dbData) {
        return ChatMessageType.from(dbData);
    }
}
