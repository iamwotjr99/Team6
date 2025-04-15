package com.team6.chat_service.auth.application.dto.internal;

public record RefreshTokenWithTTL(String token, long refreshTokenExpiration) {

}
