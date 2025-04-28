package com.team6.chat_service.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 인증/인가 관련
    INVALID_TOKEN(401, "유효하지 않는 토큰입니다."),
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    MISSING_REFRESH_TOKEN(401, "리프레시 토큰이 존재하지 않습니다."),
    UNAUTHORIZED(401, "로그인이 필요합니다."),
    FORBIDDEN(403, "접근 권한이 없습니다."),

    // 유저 관련
    NICKNAME_TOO_SHORT(400, "닉네임은 2자 이상이어야 합니다."),
    DUPLICATE_NICKNAME(409, "이미 존재하는 닉네임입니다."),
    USER_NOT_FOUND(404, "존재하지 않는 사용자입니다."),
    DUPLICATE_USER(409, "이미 가입된 사용자입니다."),

    // 채팅방 관련
    CHATROOM_TITLE_ESSENTIAL(400, "채팅방 제목은 필수입니다."),
    CHATROOM_TITLE_TOO_SHORT(400, "채팅방 제목은 2자 이상이어야 합니다."),
    CHATROOM_TITLE_TOO_LONG(400, "채팅방 제목은 20자 이하여야 합니다."),
    NO_CREATE_ANY_CHATROOM(409, "생성한 채팅방이 존재하지 않습니다."),
    CHATROOM_NOT_FOUND(404, "존재하지 않는 채팅방입니다."),

    // 채팅 관련
    CHAT_MESSAGE_TYPE_NOT_FOUND(404, "존재하지 않는 메세지 타입입니다."),
    CHAT_EVENT_TYPE_NOT_FOUND(404, "존재하지 않는 이벤트 타입입니다."),

    // 서버, 기타
    BAD_REQUEST(400, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(500, "서버 오류가 발생했습니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
