package com.team6.chat_service.user.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private Nickname nickname;
    private String kakaoEmail;
    private LocalDateTime createdAt;

    public static User createUser(Nickname nickname, String kakaoEmail) {
        return new User(null, nickname, kakaoEmail);
    }

    private User(Long id, Nickname nickname, String kakaoEmail) {
        this.id = id;
        this.nickname = nickname;
        this.kakaoEmail = kakaoEmail;
        this.createdAt = LocalDateTime.now();
    }

    public String getNicknameValue() {
        return this.nickname.getValue();
    }
}
