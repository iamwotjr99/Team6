package com.team6.chat_service.chat.domain;

import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;

public enum ChatEventType {
    ENTER,
    LEAVE,
    NONE;

    public static ChatEventType from(String value) {
        try {
            return ChatEventType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.CHAT_EVENT_TYPE_NOT_FOUND);
        }
    }
}
