package com.team6.chat_service.chat.application;

import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chat.domain.repository.ChatMessageReadRepository;
import com.team6.chat_service.chat.domain.repository.ChatMessageRepository;
import com.team6.chat_service.chat.ui.dto.ChatMessageSendRequest;
import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.chatroom.domain.repository.ChatRoomRepository;
import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;
import com.team6.chat_service.user.domain.User;
import com.team6.chat_service.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageReadRepository chatMessageReadRepository;

    @Transactional
    public ChatMessage createChatMessage(Long roomId, ChatMessageSendRequest dto) {
        ChatMessage chatMessage = ChatMessage.createChatMessage(dto.senderId(), roomId,
                dto.senderName(), dto.content(), dto.messageType(), dto.eventType());

        User user = userRepository.findById(dto.senderId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));

        return chatMessageRepository.save(chatMessage, user, chatRoom);
    }

    public List<ChatMessage> getChatMessageInRoom(Long roomId) {
        return chatMessageRepository.findByRoomId(roomId);
    }

    public List<ChatMessage> getChatMessageAfterJoinedAt(Long roomId, Long userId, LocalDateTime joinedAt) {
        List<ChatMessage> messages = chatMessageRepository.findByAfterJoinedAt(roomId,
                joinedAt);

        for (ChatMessage message : messages) {
            chatMessageReadRepository.save(message.getId(), userId);
        }

        return messages;
    }
}
