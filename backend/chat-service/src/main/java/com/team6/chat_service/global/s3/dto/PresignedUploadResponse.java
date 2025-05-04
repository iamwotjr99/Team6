package com.team6.chat_service.global.s3.dto;

public record PresignedUploadResponse(
        String key,
        String url
) {

}
