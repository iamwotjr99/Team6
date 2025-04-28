package com.team6.chat_service.chatroom.infrastructure.jpa;

import com.team6.chat_service.chatroom.domain.entity.ChatRoomUserEntity;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaChatRoomUserRepository extends JpaRepository<ChatRoomUserEntity, Long> {


    @Query("select cu.chatRoomEntity.id from ChatRoomUserEntity cu where cu.userEntity.id = :userId")
    List<Long> findChatRoomIdsByUserId(@Param("userId") Long userId);
    List<ChatRoomUserEntity> findByUserEntity_Id(Long userId);
    boolean existsByUserEntity_IdAndChatRoomEntity_Id(Long userId, Long roomId);
}
