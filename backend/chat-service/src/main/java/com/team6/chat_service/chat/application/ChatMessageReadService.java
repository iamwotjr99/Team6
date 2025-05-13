package com.team6.chat_service.chat.application;

import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chat.domain.repository.ChatMessageReadRepository;
import com.team6.chat_service.chat.infrastructure.redis.ChatMessageReadRedisRepository;
import com.team6.chat_service.chat.ui.dto.ChatMessageReadUpdateDto;
import com.team6.chat_service.chat.ui.dto.ChatMessageResponse;
import com.team6.chat_service.chatroom.application.ChatRoomService;
import com.team6.chat_service.chatroom.domain.repository.ChatRoomUserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageReadService {

    private final ChatMessageReadRedisRepository chatMessageReadRedisRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageReadRepository chatMessageReadRepository;

    @Transactional
    public List<ChatMessageResponse> getMessageWithUnreadCount(Long roomId, Long userId) {
        List<ChatMessage> messages = chatMessageService.getMessagesAfterJoinedAt(roomId, userId);
        int totalUserCount = chatRoomUserRepository.countByRoomId(roomId);

        List<ChatMessageResponse> result = messages.stream()
                .map(msg -> {
                    int readCount = (int) chatMessageReadRedisRepository.countReadUsers(msg.getId());
                    int unreadCount = totalUserCount - readCount;
                    return ChatMessageResponse.from(msg, unreadCount);
                })
                .toList();

        return result;
    }

    @Transactional
    public List<ChatMessageReadUpdateDto> markAsReadAndUpdate(List<ChatMessage> messages, Long roomId, Long userId) {
        List<ChatMessageReadUpdateDto> updates = messages.stream()
                .peek(msg -> {
                    chatMessageReadRedisRepository.markAsRead(msg.getId(), userId);
                    chatMessageReadRepository.save(msg.getId(), userId);
                })
                .map(msg -> {
                    int readUserCount = (int) chatMessageReadRedisRepository.countReadUsers(
                            msg.getId());
                    int unreadCount = Math.max(0, msg.getParticipantCount() - readUserCount);
                    return ChatMessageReadUpdateDto.from(msg.getId(), unreadCount);
                })
                .toList();

        return updates;
    }
}
