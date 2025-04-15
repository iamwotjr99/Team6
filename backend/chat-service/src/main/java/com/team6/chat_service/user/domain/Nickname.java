package com.team6.chat_service.user.domain;

import lombok.Getter;

@Getter
public class Nickname {
    private String value;

    public Nickname(String value) {
        if (value.length() < 2) {
            throw new IllegalArgumentException("닉네임은 2글자 이상");
        }

        this.value = value;
    }
}
