package com.team6.chat_service.auth.application;

import com.team6.chat_service.auth.application.dto.KakaoAuthCheckResponse;
import com.team6.chat_service.auth.application.dto.LoginResponse;
import com.team6.chat_service.auth.application.dto.TokenResponse;
import com.team6.chat_service.auth.application.dto.internal.RefreshTokenWithTTL;
import com.team6.chat_service.auth.infrastructure.KakaoClient;
import com.team6.chat_service.auth.infrastructure.dto.KakaoTokenResponse;
import com.team6.chat_service.auth.infrastructure.dto.KakaoUserResponse;
import com.team6.chat_service.auth.infrastructure.jwt.JwtProvider;
import com.team6.chat_service.auth.infrastructure.redis.RefreshTokenService;
import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;
import com.team6.chat_service.global.util.CookieUtil;
import com.team6.chat_service.user.domain.Nickname;
import com.team6.chat_service.user.domain.User;
import com.team6.chat_service.user.domain.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoClient kakaoClient;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    public KakaoAuthCheckResponse checkUserByKakaoEmail(String code) {
        KakaoTokenResponse token = kakaoClient.getToken(code);
        KakaoUserResponse userInfo = kakaoClient.getUserInfo(token.accessToken());

        String email = userInfo.kakaoAccount().email();
        Long kakoId = userInfo.id();
        boolean isRegistered = userRepository.findByKakaoEmail(email).isPresent();

        return new KakaoAuthCheckResponse(isRegistered, kakoId, email);
    }

    public LoginResponse login(String kakaoEmail) {
        User user = userRepository.findByKakaoEmail(kakaoEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String accessToken = jwtProvider.createAccessToken(user.getId());

        return new LoginResponse(
                user.getId(),
                accessToken
        );
    }

    public LoginResponse signup(String nickname, Long kakaoId, String kakaoEamil) {
        if (userRepository.findByKakaoEmail(kakaoEamil).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER);
        }

        User user = User.createUser(new Nickname(nickname), kakaoId, kakaoEamil);
        user = userRepository.save(user);

        String accessToken = jwtProvider.createAccessToken(user.getId());

        return new LoginResponse(
                user.getId(),
                accessToken
        );
    }

    public TokenResponse refreshToken(String refreshToken) {
        // String refreshToken = extractRefreshTokenFromCookie(request);

        if (!jwtProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        Long userId = jwtProvider.getUserIdFromToken(refreshToken);
        String savedToken = refreshTokenService.get(userId);

        if (savedToken == null || !savedToken.equals(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String accessToken = jwtProvider.createAccessToken(userId);
        return new TokenResponse(accessToken);
    }

    public void logout(Long userId, HttpServletResponse response) {
        refreshTokenService.delete(userId);
        CookieUtil.deleteRefreshTokenInCookie(response);
    }

    public RefreshTokenWithTTL issueRefreshToken(String kakaoEmail) {
        User user = userRepository.findByKakaoEmail(kakaoEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String refreshToken = jwtProvider.createRefreshToken(user.getId());
        long ttl = jwtProvider.getRefreshTokenExpiration();
        refreshTokenService.save(user.getId(), refreshToken, ttl);
        return new RefreshTokenWithTTL(refreshToken, ttl);
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new CustomException(ErrorCode.MISSING_REFRESH_TOKEN);
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh_token".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new CustomException(ErrorCode.MISSING_REFRESH_TOKEN));
    }
}
