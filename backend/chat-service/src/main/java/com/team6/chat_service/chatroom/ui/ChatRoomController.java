package com.team6.chat_service.chatroom.ui;

import com.team6.chat_service.auth.infrastructure.security.CustomUserDetails;
import com.team6.chat_service.chatroom.application.ChatRoomService;
import com.team6.chat_service.chatroom.domain.ChatRoom;
import com.team6.chat_service.chatroom.ui.dto.CreateChatRoomRequestDto;
import com.team6.chat_service.chatroom.ui.dto.CreateChatRoomResponseDto;
import com.team6.chat_service.chatroom.ui.dto.GetChatRoomsResponseDto;
import com.team6.chat_service.global.common.ApiResponse;
import com.team6.chat_service.global.common.ResponseFactory;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<ApiResponse<CreateChatRoomResponseDto>> createChatRoom(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody CreateChatRoomRequestDto dto
    ) {
        Long userId = customUserDetails.id();
        ChatRoom chatRoom = chatRoomService.createChatRoom(dto, userId);
        CreateChatRoomResponseDto response = CreateChatRoomResponseDto.from(chatRoom);

        return ResponseFactory.ok("채팅방 생성 성공", response);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<GetChatRoomsResponseDto>>> getChatRooms(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long userId = customUserDetails.id();
        List<ChatRoom> chatRooms = chatRoomService.getChatRooms(userId);

        List<GetChatRoomsResponseDto> response = chatRooms.stream()
                .map(GetChatRoomsResponseDto::from)
                .toList();

        return ResponseFactory.ok("채팅방 조회 성공", response);
    }
}
