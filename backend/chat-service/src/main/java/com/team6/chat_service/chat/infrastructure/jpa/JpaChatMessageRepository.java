package com.team6.chat_service.chat.infrastructure.jpa;

import com.team6.chat_service.chat.domain.entity.ChatMessageEntity;
import io.lettuce.core.dynamic.annotation.Param;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    List<ChatMessageEntity> findByChatRoomEntity_Id(Long roomId);

    @Query("SELECT m FROM ChatMessageEntity m WHERE m.chatRoomEntity.id = :roomId AND m.createdAt > :joinedAt ORDER BY m.createdAt ASC")
    List<ChatMessageEntity> findByAfterJoinedAt(@Param("roomId") Long roomId,
                                                @Param("joinedAt")LocalDateTime joinedAt);

    int countByUserEntity_Id(Long userId);
}
