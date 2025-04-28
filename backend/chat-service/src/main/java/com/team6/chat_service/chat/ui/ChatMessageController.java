package com.team6.chat_service.chat.ui;

import com.team6.chat_service.chat.application.ChatMessageService;
import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.global.common.ApiResponse;
import com.team6.chat_service.global.common.ResponseFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatmessage")
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<List<ChatMessage>>> getChatMessageInRoom(
            @PathVariable(name = "roomId") Long roomId
    ) {
        List<ChatMessage> response = chatMessageService.getChatMessageInRoom(roomId);

        return ResponseFactory.ok("채팅 내역 조회 성공", response);
    }
}
