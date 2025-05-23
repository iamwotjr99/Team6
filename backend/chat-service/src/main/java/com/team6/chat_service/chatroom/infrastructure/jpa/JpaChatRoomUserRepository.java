package com.team6.chat_service.chatroom.infrastructure.jpa;

import com.team6.chat_service.chatroom.domain.entity.ChatRoomUserEntity;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaChatRoomUserRepository extends JpaRepository<ChatRoomUserEntity, Long> {


    @Query("select cu.chatRoomEntity.id from ChatRoomUserEntity cu where cu.userEntity.id = :userId")
    List<Long> findChatRoomIdsByUserId(@Param("userId") Long userId);

    List<ChatRoomUserEntity> findByUserEntity_Id(Long userId);

    Optional<ChatRoomUserEntity> findByUserEntity_IdAndChatRoomEntity_Id(Long userId, Long roomId);

    boolean existsByUserEntity_IdAndChatRoomEntity_Id(Long userId, Long roomId);

    int deleteByUserEntity_IdAndChatRoomEntity_Id(Long userId, Long roomId);

    int countByChatRoomEntity_Id(Long roomId);

    int countByUserEntity_Id(Long userId);

    @Query("select cu.userEntity.id from ChatRoomUserEntity cu where cu.chatRoomEntity.id = :roomId")
    Set<Long> findUserIdsByRoomId(@Param("roomId") Long roomId);

    List<ChatRoomUserEntity> findByChatRoomEntity_Id(Long roomId);
}
