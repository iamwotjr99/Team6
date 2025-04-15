package com.team6.chat_service.user.infrastructure;

import com.team6.chat_service.user.domain.User;
import com.team6.chat_service.user.domain.entity.UserEntity;
import com.team6.chat_service.user.domain.repository.UserRepository;
import com.team6.chat_service.user.infrastructure.jpa.JpaUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity(user);
        userEntity = jpaUserRepository.save(userEntity);
        return userEntity.toUser();
    }

    @Override
    public Optional<User> findByKakaoEmail(String kakaoEmail) {
        return jpaUserRepository.findByKakaoEmail(kakaoEmail)
                .map(UserEntity::toUser);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(UserEntity::toUser);
    }
}
