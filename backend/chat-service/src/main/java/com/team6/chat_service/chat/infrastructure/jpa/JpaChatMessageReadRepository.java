package com.team6.chat_service.chat.infrastructure.jpa;

import com.team6.chat_service.chat.domain.entity.ChatMessageReadEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaChatMessageReadRepository extends JpaRepository<ChatMessageReadEntity, Long> {

    boolean existsByChatMessageEntity_IdAndUserEntity_Id(Long chatMessageId, Long userId);

    @Query("""
      SELECT COUNT(m)
      FROM ChatMessageEntity m
      WHERE m.chatRoomEntity.id = :roomId
        AND NOT exists (
          SELECT 1 FROM ChatMessageReadEntity r
          WHERE r.chatMessageEntity.id = m.id
            AND r.userEntity.id = :userId
        )
""")
    int countUnreadMessagesByRoomIdAndUserId(@Param("roomId") Long roomId,
                                             @Param("userId") Long userId);
}
