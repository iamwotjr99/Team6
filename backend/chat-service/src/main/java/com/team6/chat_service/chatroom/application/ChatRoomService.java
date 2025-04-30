package com.team6.chat_service.chatroom.application;

import com.team6.chat_service.chat.ui.dto.ChatMessageSendRequest;
import com.team6.chat_service.chatroom.application.dto.CreateChatRoomDto;
import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.chatroom.domain.ChatRoomTitle;
import com.team6.chat_service.chatroom.domain.ChatRoomUser;
import com.team6.chat_service.chatroom.domain.repository.ChatRoomRepository;
import com.team6.chat_service.chatroom.domain.repository.ChatRoomUserRepository;
import com.team6.chat_service.chatroom.ui.dto.CreateChatRoomRequestDto;
import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;
import com.team6.chat_service.user.domain.User;
import com.team6.chat_service.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatRoom createChatRoom(CreateChatRoomRequestDto dto, Long userId) {
        ChatRoom chatRoom = ChatRoom.create(userId,
                new ChatRoomTitle(dto.title()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom, user);

        chatRoomUserRepository.save(savedChatRoom, user);

        return savedChatRoom;
    }

    @Transactional
    public List<ChatRoom> getMyChatRooms(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Long> chatRoomIds = chatRoomUserRepository.findChatRoomIdsByUserId(userId);
        List<ChatRoom> chatRoomList = chatRoomRepository.findChatRoomsByIds(chatRoomIds);

        return chatRoomList;
    }

    public List<ChatRoom> getChatRooms() {
        List<ChatRoom> chatRoomList = chatRoomRepository.findAllByOrderByLastMessageAtDesc();

        return chatRoomList;
    }

    public ChatRoomUser getChatRoomUserByUserIdAndRoomId(Long userId, Long roomId) {
        return chatRoomUserRepository.findChatRoomUserByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_USER_NOT_FOUND));
    }

    @Transactional
    public boolean enterChatRoom(Long userId, Long roomId) {
        boolean alreadyEntered = chatRoomUserRepository.existsByUserIdAndRoomId(userId, roomId);

        if(!alreadyEntered) {
            ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                    .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            chatRoomUserRepository.save(chatRoom, user);
        }

        return alreadyEntered;
    }

    @Transactional
    public boolean leaveChatRoom(Long userId, Long roomId) {
        boolean alreadyEntered = chatRoomUserRepository.existsByUserIdAndRoomId(userId, roomId);

        if (alreadyEntered) {
            int deleteCount = chatRoomUserRepository.deleteByUserIdAndRoomId(userId, roomId);
            return deleteCount > 0;
        } else {
            return false;
        }
    }
}
