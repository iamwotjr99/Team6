package com.team6.chat_service.chat.domain.repository;

public interface ChatMessageReadRepository {
    void save(Long chatMessageId, Long userId);

    int countUnreadMessagesByRoomIdAndUserId(Long roomId, Long userId);
}
