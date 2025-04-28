package com.team6.chat_service.chat.infrastructure.jpa;

import com.team6.chat_service.chat.domain.entity.ChatMessageEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    List<ChatMessageEntity> findByChatRoomEntity_Id(Long roomId);
}
