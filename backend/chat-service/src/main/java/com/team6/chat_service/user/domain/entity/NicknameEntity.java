package com.team6.chat_service.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NicknameEntity {

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;
}
