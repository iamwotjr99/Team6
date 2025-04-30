package com.team6.chat_service.chat.domain.entity;

import com.team6.chat_service.chat.domain.ChatMessageRead;
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

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "message_reads")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageReadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private ChatMessageEntity chatMessageEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @CreatedDate
    @Column(name = "read_at")
    private LocalDateTime readAt;

    public ChatMessageReadEntity(ChatMessageRead chatMessageRead, ChatMessageEntity chatMessageEntity, UserEntity userEntity) {
        this.id = chatMessageRead.getId();
        this.chatMessageEntity = chatMessageEntity;
        this.userEntity = userEntity;
        this.readAt = chatMessageRead.getReadAt();
    }

    public ChatMessageRead toChatMessageRead() {
        return ChatMessageRead.builder()
                .id(this.id)
                .chatMessageId(this.chatMessageEntity.getId())
                .userId(this.userEntity.getId())
                .readAt(this.readAt)
                .build();
    }

}
