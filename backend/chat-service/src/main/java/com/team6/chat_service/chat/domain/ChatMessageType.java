package com.team6.chat_service.chat.domain;

import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;

public enum ChatMessageType {
    TEXT,
    PICTURE,
    VIDEO;

    public static ChatMessageType from(String value) {
        try {
            return ChatMessageType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.CHAT_MESSAGE_TYPE_NOT_FOUND);
        }
    }
}
