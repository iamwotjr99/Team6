package com.team6.chat_service.user.ui;

import com.team6.chat_service.auth.application.AuthService;
import com.team6.chat_service.auth.infrastructure.security.CustomUserDetails;
import com.team6.chat_service.global.common.ApiResponse;
import com.team6.chat_service.global.common.ResponseFactory;
import com.team6.chat_service.user.application.UserService;
import com.team6.chat_service.user.application.dto.DuplicateNicknameCheckResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/nickname/check")
    // /nickname/check?nickname=jaesuk
    public ResponseEntity<ApiResponse<DuplicateNicknameCheckResponseDto>> checkNickname(@RequestParam String nickname) {
        DuplicateNicknameCheckResponseDto result = userService.checkDuplicateNickname(nickname);

        return ResponseFactory.ok("사용 가능한 닉네임", result);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUser(@AuthenticationPrincipal CustomUserDetails customUserDetails, HttpServletResponse response) {
        userService.deleteByUserId(customUserDetails.id());

        authService.logout(customUserDetails.id(), response);

        return ResponseFactory.ok("회원 탈퇴 성공", null);
    }
}
