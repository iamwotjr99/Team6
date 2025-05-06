package com.team6.chat_service.chatroom.infrastructure.jpa;

import com.team6.chat_service.chatroom.domain.entity.ChatRoomEntity;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    List<ChatRoomEntity> findByCreator_Id(Long userId);

    List<ChatRoomEntity> findByIdIn(List<Long> ids);

    List<ChatRoomEntity> findAllByOrderByLastMessageAtDesc();

    @Query("""
      SELECT c FROM ChatRoomEntity c
      WHERE c.id IN (
        SELECT cu.chatRoomEntity.id FROM ChatRoomUserEntity cu WHERE cu.userEntity.id = :userId
      )
""")
    List<ChatRoomEntity> findJoinedByUserId(@Param("userId") Long userId);

    @Query("""
      SELECT c FROM ChatRoomEntity c
      WHERE c.id NOT IN (
        SELECT cu.chatRoomEntity.id FROM ChatRoomUserEntity cu WHERE cu.userEntity.id = :userId
      )
""")
    List<ChatRoomEntity> findNotJoinedByUserId(@Param("userId") Long userId);

    int countByCreator_Id(Long userId);

}
