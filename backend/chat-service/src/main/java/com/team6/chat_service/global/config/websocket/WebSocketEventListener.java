package com.team6.chat_service.global.config.websocket;

import com.team6.chat_service.chatroom.application.ChatRoomOnlineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Log4j2
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final ChatRoomOnlineService chatRoomOnlineService;

    @EventListener
    public void handlerConnect(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String userIdStr = (String) accessor.getSessionAttributes().get("userId");
        String roomIdStr = (String) accessor.getSessionAttributes().get("roomId");

        log.info("사용자 ID {} 가 채팅방 {} 에 접속", userIdStr, roomIdStr);

        if (userIdStr != null && roomIdStr != null) {
            Long userId = Long.parseLong(userIdStr);
            Long roomId = Long.parseLong(roomIdStr);
            chatRoomOnlineService.connectUserToRoom(roomId, userId);
        } else if (userIdStr != null) {
            log.info("사용자 ID {} 가 유저 전용 채널로 접속(로그인)", userIdStr);
        }
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String userIdStr = (String) accessor.getSessionAttributes().get("userId");
        String roomIdStr = (String) accessor.getSessionAttributes().get("roomId");

        log.info("사용자 ID {} 가 채팅방 {} 에서 나감", userIdStr, roomIdStr);

        if (userIdStr != null && roomIdStr != null) {
            Long userId = Long.parseLong(userIdStr);
            Long roomId = Long.parseLong(roomIdStr);
            chatRoomOnlineService.disconnectUserToRoom(roomId, userId);
        } else if (userIdStr != null) {
            log.info("사용자 ID {} 가 유저 전용 채널에서 나감(로그인 해제)", userIdStr);
        }
    }
}
