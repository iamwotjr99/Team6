package com.team6.chat_service.chatroom.infrastructure.jpa;

import com.team6.chat_service.chatroom.domain.entity.ChatRoomEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    List<ChatRoomEntity> findByCreator_Id(Long userId);

    List<ChatRoomEntity> findByIdIn(List<Long> ids);

    List<ChatRoomEntity> findAllByOrderByLastMessageAtDesc();

}
