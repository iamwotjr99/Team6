package com.team6.chat_service.chat.ui;

import com.team6.chat_service.auth.infrastructure.security.CustomUserDetails;
import com.team6.chat_service.chat.application.ChatMessageService;
import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chatroom.application.ChatRoomService;
import com.team6.chat_service.chatroom.domain.ChatRoomUser;
import com.team6.chat_service.global.common.ApiResponse;
import com.team6.chat_service.global.common.ResponseFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatroom")
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    @GetMapping("/{roomId}/messages/all")
    public ResponseEntity<ApiResponse<List<ChatMessage>>> getChatMessageInRoom(
            @PathVariable(name = "roomId") Long roomId
    ) {
        List<ChatMessage> response = chatMessageService.getChatMessageInRoom(roomId);

        return ResponseFactory.ok("모든 채팅 내역 조회 성공", response);
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<ApiResponse<List<ChatMessage>>> getChatMessageAfterJoinedAt(
            @PathVariable(name = "roomId") Long roomId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        ChatRoomUser chatRoomUser = chatRoomService.getChatRoomUserByUserIdAndRoomId(
                customUserDetails.id(), roomId);

        List<ChatMessage> response = chatMessageService.getChatMessageAfterJoinedAt(
                roomId, customUserDetails.id(), chatRoomUser.getJoinedAt());

        return ResponseFactory.ok("채팅 내역 조회 성공", response);
    }
}
