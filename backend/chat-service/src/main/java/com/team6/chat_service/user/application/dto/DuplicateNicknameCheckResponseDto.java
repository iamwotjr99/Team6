package com.team6.chat_service.user.application.dto;

public record DuplicateNicknameCheckResponseDto(
        String nickname,
        boolean available
) {

}
