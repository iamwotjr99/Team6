package com.team6.chat_service.chatroom.application;

import com.team6.chat_service.chatroom.application.dto.CreateChatRoomDto;
import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.chatroom.domain.ChatRoomTitle;
import com.team6.chat_service.chatroom.domain.repository.ChatRoomRepository;
import com.team6.chat_service.chatroom.domain.repository.ChatRoomUserRepository;
import com.team6.chat_service.chatroom.ui.dto.CreateChatRoomRequestDto;
import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;
import com.team6.chat_service.user.domain.User;
import com.team6.chat_service.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
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

    public List<ChatRoom> getChatRooms(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Long> chatRoomIds = chatRoomUserRepository.findChatRoomIdsByUserId(userId);
        List<ChatRoom> chatRoomList = chatRoomRepository.findChatRoomsByIds(chatRoomIds);

        return chatRoomList;
    }
}
