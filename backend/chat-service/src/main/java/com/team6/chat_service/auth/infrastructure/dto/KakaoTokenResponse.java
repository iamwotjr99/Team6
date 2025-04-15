package com.team6.chat_service.auth.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record KakaoTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("expires_in") Long expiresIn,
        @JsonProperty("scope") String scope,
        @JsonProperty("refresh_token_expires_in") Long refreshTokenExpiresIn) {
}
