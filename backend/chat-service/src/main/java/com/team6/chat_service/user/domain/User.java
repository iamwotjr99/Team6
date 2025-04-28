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
    private Long kakaoId;
    private Nickname nickname;
    private String kakaoEmail;
    private LocalDateTime createdAt;

    public static User createUser(Nickname nickname, Long kakaoId, String kakaoEmail) {
        return new User(null, nickname, kakaoId, kakaoEmail);
    }

    private User(Long id, Nickname nickname, Long kakaoId, String kakaoEmail) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.kakaoEmail = kakaoEmail;
        this.createdAt = LocalDateTime.now();
    }

    public String getNicknameValue() {
        return this.nickname.getValue();
    }
}
