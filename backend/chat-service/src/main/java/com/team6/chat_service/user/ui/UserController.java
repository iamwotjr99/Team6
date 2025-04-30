package com.team6.chat_service.user.ui;

import com.team6.chat_service.auth.application.AuthService;
import com.team6.chat_service.auth.infrastructure.KakaoClient;
import com.team6.chat_service.auth.infrastructure.security.CustomUserDetails;
import com.team6.chat_service.global.common.ApiResponse;
import com.team6.chat_service.global.common.ResponseFactory;
import com.team6.chat_service.user.application.UserService;
import com.team6.chat_service.user.application.dto.DuplicateNicknameCheckResponseDto;
import com.team6.chat_service.user.domain.User;
import com.team6.chat_service.user.ui.dto.GetUserInfoRequestDto;
import com.team6.chat_service.user.ui.dto.GetUserInfoResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final KakaoClient kakaoClient;

    @GetMapping("/nickname/check")
    // /nickname/check?nickname=jaesuk
    public ResponseEntity<ApiResponse<DuplicateNicknameCheckResponseDto>> checkNickname(@RequestParam String nickname) {
        DuplicateNicknameCheckResponseDto result = userService.checkDuplicateNickname(nickname);

        return ResponseFactory.ok("사용 가능한 닉네임", result);
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<GetUserInfoResponseDto>> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.id();
        User userInfo = userService.getUserInfoById(userId);

        GetUserInfoResponseDto response = new GetUserInfoResponseDto(
                userInfo.getNicknameValue(),
                userInfo.getKakaoEmail(),
                userInfo.getCreatedAt()
        );

        return ResponseFactory.ok("유저 정보 조회 성공", response);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUser(@AuthenticationPrincipal CustomUserDetails customUserDetails, HttpServletResponse response) {
        User user = userService.getUserInfoById(customUserDetails.id());

        kakaoClient.unlinkUserByAdminKey(user.getKakaoId());

        userService.deleteByUserId(customUserDetails.id());

        authService.logout(customUserDetails.id(), response);

        return ResponseFactory.ok("회원 탈퇴 성공", null);
    }
}
