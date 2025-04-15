package com.team6.chat_service.auth.application.dto;

public record LoginResponse(
        Long id,
        String accessToken
) {
}
