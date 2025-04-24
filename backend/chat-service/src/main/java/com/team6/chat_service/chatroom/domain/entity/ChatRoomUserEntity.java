package com.team6.chat_service.chatroom.domain.entity;

import com.team6.chat_service.chatroom.domain.ChatRoomUser;
import com.team6.chat_service.user.domain.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "chatroom_users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoomEntity chatRoomEntity;

    @CreatedDate
    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    public ChatRoomUserEntity(UserEntity userEntity, ChatRoomEntity chatRoomEntity) {
        this.userEntity = userEntity;
        this.chatRoomEntity = chatRoomEntity;
        this.joinedAt = LocalDateTime.now();
    }

    public ChatRoomUser toChatRoomUser() {
        return ChatRoomUser.builder()
                .id(this.id)
                .chatRoomId(this.chatRoomEntity.getId())
                .userId(this.userEntity.getId())
                .joinedAt(this.joinedAt)
                .build();
    }

}
