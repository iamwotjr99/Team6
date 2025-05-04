package com.team6.chat_service.global.s3;

import com.team6.chat_service.global.common.ApiResponse;
import com.team6.chat_service.global.common.ResponseFactory;
import com.team6.chat_service.global.s3.dto.PresignedReadResponse;
import com.team6.chat_service.global.s3.dto.PresignedUploadResponse;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dev/s3")
public class S3PresignedUrlController {

    private final S3PresignedUrlService s3PresignedUrlService;

    // 업로드용 PUT URL 응답
    @GetMapping("/presigned-url")
    public ResponseEntity<ApiResponse<PresignedUploadResponse>> getPresignedUrl(
            @RequestParam String fileName,
            @RequestParam String contentType,
            @RequestParam Long userId
    ) {
        PresignedUploadResponse response = s3PresignedUrlService.generatePresignedUploadUrl(
                fileName, contentType, userId);
        return ResponseFactory.ok("S3로부터 PUT URL 응답 성공", response);
    }

    // 읽기용 GET URL 응답
    @GetMapping("/presigned-get-url")
    public ResponseEntity<ApiResponse<PresignedReadResponse>> getPresignedGetUrl(@RequestParam String key) {
        URL url = s3PresignedUrlService.generatePresignedGetUrl(key);
        return ResponseFactory.ok("S3로부터 GET URL 응답 성공", new PresignedReadResponse(url.toString()));
    }

}
