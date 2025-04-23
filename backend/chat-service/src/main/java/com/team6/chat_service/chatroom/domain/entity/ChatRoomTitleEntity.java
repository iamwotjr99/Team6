package com.team6.chat_service.chatroom.domain.entity;

import com.team6.chat_service.chatroom.domain.ChatRoomTitle;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatRoomTitleEntity {
    @Column(name = "title")
    private String title;
}
