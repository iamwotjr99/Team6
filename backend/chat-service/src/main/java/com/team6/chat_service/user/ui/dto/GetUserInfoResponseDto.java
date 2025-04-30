package com.team6.chat_service.user.ui.dto;

import java.time.LocalDateTime;

public record GetUserInfoResponseDto(
        String nickname,
        String kakaoEmail,
        LocalDateTime created_at
        ) {
}
