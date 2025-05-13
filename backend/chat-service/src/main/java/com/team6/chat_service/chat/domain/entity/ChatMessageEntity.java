package com.team6.chat_service.chat.domain.entity;

import com.team6.chat_service.chat.domain.ChatEventType;
import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chat.domain.ChatMessageType;
import com.team6.chat_service.chatroom.domain.entity.ChatRoomEntity;
import com.team6.chat_service.user.domain.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "chats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoomEntity chatRoomEntity;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "content")
    private String content;

    @Convert(converter = ChatMessageTypeConverter.class)
    @Column(name = "message_type")
    private ChatMessageType messageType;

    @Convert(converter = ChatEventTypeConverter.class)
    @Column(name = "event_type")
    private ChatEventType eventType;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "participant_count")
    private Integer participantCount = 0;

    public ChatMessageEntity(ChatMessage chatMessage, UserEntity userEntity, ChatRoomEntity chatRoomEntity) {
        this.id = chatMessage.getId();
        this.userEntity = userEntity;
        this.chatRoomEntity = chatRoomEntity;
        this.senderName = chatMessage.getSenderName();
        this.content = chatMessage.getContent();
        this.messageType = chatMessage.getMessageType();
        this.eventType = chatMessage.getEventType();
        this.createdAt = chatMessage.getCreatedAt();
        this.participantCount = chatMessage.getParticipantCount();
    }

    public ChatMessage toChatMessage() {
        return ChatMessage.builder()
                .id(this.id)
                .senderId(this.userEntity.getId())
                .roomId(this.chatRoomEntity.getId())
                .senderName(this.senderName)
                .content(this.content)
                .messageType(this.messageType)
                .eventType(this.eventType)
                .createdAt(this.createdAt)
                .participantCount(this.participantCount)
                .build();
    }
}
