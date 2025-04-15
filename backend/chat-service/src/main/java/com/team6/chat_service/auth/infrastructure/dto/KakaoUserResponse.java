package com.team6.chat_service.auth.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserResponse(
        Long id,
        @JsonProperty("connected_at") String connectedAt,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {
    public record KakaoAccount(
            @JsonProperty("has_email") boolean hasEmail,
            @JsonProperty("email_needs_agreement") boolean emailNeedsAgreement,
            @JsonProperty("is_email_valid") boolean isEmailValid,
            @JsonProperty("is_email_verified") boolean isEmailVerified,
            String email
    ) {}
}
