package com.team6.chat_service.chatroom.application;

import com.team6.chat_service.chatroom.infrastructure.redis.ChatRoomOnlineRedisRepository;
import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;
import com.team6.chat_service.user.domain.User;
import com.team6.chat_service.user.domain.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomOnlineService {
    private final ChatRoomOnlineRedisRepository chatRoomOnlineRedisRepository;
    private final UserRepository userRepository;

    public void connectUserToRoom(Long roomId, Long userId) {
        chatRoomOnlineRedisRepository.addOnlineUser(roomId, userId);
    }

    public void disconnectUserToRoom(Long roomId, Long userId) {
        chatRoomOnlineRedisRepository.removeOnlineUser(roomId, userId);
    }

    public List<User> getOnlineUsersInRoom(Long roomId) {
        List<Long> onlineUserIds = chatRoomOnlineRedisRepository.getOnlineUserIds(roomId).stream()
                .map(val -> Long.parseLong(val)).toList();

        List<User> result = new ArrayList<>();
        for(Long id: onlineUserIds) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            result.add(user);
        }

        return result;
    }

    public Set<String> getOnlineUserIds(Long roomId) {
        return chatRoomOnlineRedisRepository.getOnlineUserIds(roomId);
    }

    public long getOnlineUserCount(Long roomId) {
        return chatRoomOnlineRedisRepository.countOnlineUsers(roomId);
    }
}
