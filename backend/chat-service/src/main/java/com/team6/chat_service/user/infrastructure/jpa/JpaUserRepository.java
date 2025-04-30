package com.team6.chat_service.user.infrastructure.jpa;

import com.team6.chat_service.user.domain.entity.NicknameEntity;
import com.team6.chat_service.user.domain.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByKakaoEmail(String kakaoEmail);
    boolean existsByNickname(NicknameEntity nickname);
}
