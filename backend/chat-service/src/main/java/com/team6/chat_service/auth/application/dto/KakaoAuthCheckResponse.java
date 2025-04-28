package com.team6.chat_service.auth.application.dto;

public record KakaoAuthCheckResponse(
        boolean isRegistered,
        Long kakaoId,
        String kakaoEmail
) {

}
