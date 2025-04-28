package com.team6.chat_service.auth.infrastructure;

import com.team6.chat_service.auth.infrastructure.dto.KakaoTokenResponse;
import com.team6.chat_service.auth.infrastructure.dto.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class KakaoClient {

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${oauth.kakao.token-uri}")
    private String tokenUri;

    @Value("${oauth.kakao.user-info-uri}")
    private String userInfoUri;

    @Value("${oauth.kakao.admin-key}")
    private String adminKey;

    private final WebClient webClient = WebClient.builder().build();

    // 프론트에서 받은 인가 코드로 카카오에게 엑세스 토큰 요청
    public KakaoTokenResponse getToken(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);

        return webClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .block();
    }

    // 카카오에게 access_token으로 사용자 정보 요청
    public KakaoUserResponse getUserInfo(String accessToken) {
        return webClient.get()
                .uri(userInfoUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserResponse.class)
                .block();
    }

    public void unlinkUserByAdminKey(Long kakaoUserId) {
        webClient.post()
                .uri("https://kapi.kakao.com/v1/user/unlink")
                .header(HttpHeaders.AUTHORIZATION, "KakaoAK " + adminKey)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("target_id_type", "user_id")
                        .with("target_id", kakaoUserId.toString()))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}
