package com.team6.chat_service.auth.application.dto;

public record SignupRequestDto(
        String nickname,
        String kakaoEmail
) {

}
