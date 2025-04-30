package com.team6.chat_service.chat.ui;

import com.team6.chat_service.chat.application.ChatMessageService;
import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chatroom.application.ChatRoomService;
import com.team6.chat_service.chatroom.domain.ChatRoomUser;
import com.team6.chat_service.global.common.ApiResponse;
import com.team6.chat_service.global.common.ResponseFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dev")
@RequiredArgsConstructor
public class ChatMessageDevController {
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    // 테스트 전용 API
    @GetMapping("/chatroom/{roomId}/messages")
    public ResponseEntity<ApiResponse<List<ChatMessage>>> getTestChatMessages(
            @PathVariable Long roomId,
            @RequestParam Long userId
    ) {
        ChatRoomUser chatRoomUser = chatRoomService.getChatRoomUserByUserIdAndRoomId(userId, roomId);
        List<ChatMessage> response = chatMessageService.getChatMessageAfterJoinedAt(roomId, chatRoomUser.getJoinedAt());
        return ResponseFactory.ok("채팅 내역 조회 성공", response);
    }
}
