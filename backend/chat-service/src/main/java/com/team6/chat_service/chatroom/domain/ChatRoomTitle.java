package com.team6.chat_service.chatroom.domain;

import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomTitle {
    private String value;

    public ChatRoomTitle(String value) {
        if (value == null || value.isBlank()) {
            throw new CustomException(ErrorCode.CHATROOM_TITLE_ESSENTIAL);
        }
        if (value.length() < 2) {
            throw new CustomException(ErrorCode.CHATROOM_TITLE_TOO_SHORT);
        }
        if (value.length() > 20) {
            throw new CustomException(ErrorCode.CHATROOM_TITLE_TOO_LONG);
        }
        this.value = value;
    }
}
