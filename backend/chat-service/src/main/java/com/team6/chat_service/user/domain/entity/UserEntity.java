package com.team6.chat_service.user.domain.entity;

import com.team6.chat_service.user.domain.Nickname;
import com.team6.chat_service.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id")
    private Long kakaoId;

    @Embedded
    private NicknameEntity nickname;

    @Column(name = "kakao_email")
    private String kakaoEmail;

    @CreatedDate
    private LocalDateTime createdAt;

    public UserEntity(User user) {
        this.id = user.getId();
        this.kakaoId = user.getKakaoId();
        this.nickname = new NicknameEntity(user.getNicknameValue());
        this.kakaoEmail = user.getKakaoEmail();
        this.createdAt = user.getCreatedAt();
    }

    public User toUser() {
        return User.builder()
                .id(this.id)
                .kakaoId(this.kakaoId)
                .nickname(new Nickname(this.nickname.getNickname()))
                .kakaoEmail(this.kakaoEmail)
                .createdAt(createdAt)
                .build();
    }
}
