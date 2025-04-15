package com.team6.chat_service.auth.ui;

import com.team6.chat_service.auth.application.AuthService;
import com.team6.chat_service.auth.application.dto.KakaoAuthCheckRequestDto;
import com.team6.chat_service.auth.application.dto.KakaoAuthCheckResponse;
import com.team6.chat_service.auth.application.dto.LoginRequestDto;
import com.team6.chat_service.auth.application.dto.LoginResponse;
import com.team6.chat_service.auth.application.dto.SignupRequestDto;
import com.team6.chat_service.auth.application.dto.TokenResponse;
import com.team6.chat_service.auth.application.dto.internal.RefreshTokenWithTTL;
import com.team6.chat_service.auth.infrastructure.security.CustomUserDetails;
import com.team6.chat_service.global.common.ApiResponse;
import com.team6.chat_service.global.common.ResponseFactory;
import com.team6.chat_service.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/oauth/kakao/check")
    public ResponseEntity<ApiResponse<KakaoAuthCheckResponse>> kakaoAuthCheck(@RequestBody
            KakaoAuthCheckRequestDto kakaoAuthCheckRequestDto) {
        KakaoAuthCheckResponse kakaoAuthCheckResponse = authService.checkUserByKakaoEmail(
                kakaoAuthCheckRequestDto.code());

        return ResponseFactory.ok("카카오 auth", kakaoAuthCheckResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response) {
        LoginResponse result = authService.login(loginRequestDto.kakaoEmail());

        RefreshTokenWithTTL refreshTokenDto = authService.issueRefreshToken(loginRequestDto.kakaoEmail());
        CookieUtil.setRefreshTokenInCookie(response, refreshTokenDto.token(), refreshTokenDto.refreshTokenExpiration());

        return ResponseFactory.ok("로그인 성공", result);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<LoginResponse>> signup(@RequestBody SignupRequestDto signupRequestDto,
            HttpServletResponse response) {
        LoginResponse result = authService.signup(signupRequestDto.nickname(),
                signupRequestDto.kakaoEmail());

        RefreshTokenWithTTL refreshTokenDto = authService.issueRefreshToken(signupRequestDto.kakaoEmail());
        CookieUtil.setRefreshTokenInCookie(response, refreshTokenDto.token(), refreshTokenDto.refreshTokenExpiration());

        return ResponseFactory.created("회원가입 성공", result);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(
            @CookieValue("refresh_token") String refreshToken
    ) {
        TokenResponse tokenResponse = authService.refreshToken(refreshToken);

        return ResponseFactory.ok("토큰 리프레쉬 성공", tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal CustomUserDetails customUserDetails, HttpServletResponse response) {
        authService.logout(customUserDetails.id(), response);

        return ResponseFactory.ok("로그아웃 성공", null);
    }
}


