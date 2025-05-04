package com.team6.chat_service.user.application;

import com.team6.chat_service.chatroom.domain.repository.ChatRoomUserRepository;
import com.team6.chat_service.chatroom.infrastructure.redis.ChatRoomOnlineRedisRepository;
import com.team6.chat_service.global.exception.CustomException;
import com.team6.chat_service.global.exception.ErrorCode;
import com.team6.chat_service.user.application.dto.DuplicateNicknameCheckResponseDto;
import com.team6.chat_service.user.domain.User;
import com.team6.chat_service.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatRoomOnlineRedisRepository chatRoomOnlineRedisRepository;

    public DuplicateNicknameCheckResponseDto checkDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        return new DuplicateNicknameCheckResponseDto(nickname, true);
    }

    public User getUserInfoById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user;
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        userRepository.deleteByUserId(userId);
    }

    @Transactional
    public Set<Long> getOfflineUserIds(Long roomId) {
        Set<Long> allUserIds = chatRoomUserRepository.findUserIdsByRoomId(roomId);
        Set<Long> onlineUserIds = chatRoomOnlineRedisRepository.getOnlineUserIds(roomId)
                .stream()
                .map(userId -> Long.parseLong(userId))
                .collect(Collectors.toSet());

        Set<Long> offlineUserIds = allUserIds.stream()
                .filter(userId -> !onlineUserIds.contains(userId))
                .collect(Collectors.toSet());

        return offlineUserIds;
    }

}
