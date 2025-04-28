package com.team6.chat_service.user.domain.repository;

import com.team6.chat_service.user.domain.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByKakaoEmail(String kakaoEmail);
    Optional<User> findByNickname(String nickname);

    Optional<User> findById(Long id);

    boolean existsByNickname(String nickname);

    void deleteByUserId(Long userId);
}
