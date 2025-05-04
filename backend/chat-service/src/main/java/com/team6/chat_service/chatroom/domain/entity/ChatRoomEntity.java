package com.team6.chat_service.chatroom.domain.entity;

import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.chatroom.domain.ChatRoomTitle;
import com.team6.chat_service.user.domain.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chatrooms")
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity creator;

    @Embedded
    private ChatRoomTitleEntity titleEntity;

    @Column(name = "last_message")
    private String lastMessage;

    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ChatRoomEntity(ChatRoom chatRoom, UserEntity creator) {
        this.id = chatRoom.getId();
        this.creator = creator;
        this.titleEntity = new ChatRoomTitleEntity(chatRoom.getTitle().getValue());
        this.lastMessage = chatRoom.getLastMessage();
        this.lastMessageAt = chatRoom.getLastMessageAt();
        this.createdAt = LocalDateTime.now();
    }

    public void updateFromDomain(ChatRoom chatRoom) {
        this.lastMessage = chatRoom.getLastMessage();
        this.lastMessageAt = chatRoom.getLastMessageAt();
    }

    public ChatRoom toChatRoom() {
        return ChatRoom.builder()
                .id(id)
                .userId(creator.getId())
                .title(new ChatRoomTitle(titleEntity.getTitle()))
                .lastMessage(lastMessage)
                .lastMessageAt(lastMessageAt)
                .createdAt(createdAt)
                .build();
    }
}
