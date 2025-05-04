package com.team6.chat_service.chat.ui;

import com.team6.chat_service.chat.application.ChatMessageReadService;
import com.team6.chat_service.chat.application.ChatMessageService;
import com.team6.chat_service.chat.domain.ChatEventType;
import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chat.domain.ChatMessageType;
import com.team6.chat_service.chat.infrastructure.redis.ChatMessageReadRedisRepository;
import com.team6.chat_service.chat.ui.dto.ChatMessageReadUpdateDto;
import com.team6.chat_service.chat.ui.dto.ChatMessageResponse;
import com.team6.chat_service.chat.ui.dto.ChatMessageSendRequest;
import com.team6.chat_service.chat.ui.dto.UnreadSummaryDto;
import com.team6.chat_service.chatroom.application.ChatRoomOnlineService;
import com.team6.chat_service.chatroom.application.ChatRoomService;
import com.team6.chat_service.user.application.UserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageReadService chatMessageReadService;
    private final ChatMessageReadRedisRepository chatMessageReadRedisRepository;
    private final ChatRoomOnlineService chatRoomOnlineService;
    private final UserService userService;

    @MessageMapping("/chatroom/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, @Payload ChatMessageSendRequest message) {
        log.info("Sending message: {}", message);

        handleChatMessage(roomId, message);
    }

    @MessageMapping("/chatroom/{roomId}/enter")
    public void enterMessage(@DestinationVariable Long roomId, @Payload ChatMessageSendRequest message) {
        log.info("Enter message: {}", message);

        handleEnter(roomId, message);
    }

    @MessageMapping("/chatroom/{roomId}/leave")
    public void leaveMessage(@DestinationVariable Long roomId, @Payload ChatMessageSendRequest message) {
        log.info("Leave message: {}", message);

        handleLeave(roomId, message);
    }


    private void handleEnter(Long roomId, ChatMessageSendRequest message) {
        boolean alreadyEntered = chatRoomService.enterChatRoom(message.senderId(), roomId);

        if(!alreadyEntered) {
            ChatMessage enterMessage = ChatMessage.createChatMessage(
                    message.senderId(),
                    roomId,
                    message.senderName(),
                    message.senderName() + "님이 입장했습니다.",
                    ChatMessageType.TEXT.name(),
                    ChatEventType.ENTER.name()
            );
            sendChatMessageToRoom(roomId, ChatMessageSendRequest.from(enterMessage));
        }

        List<ChatMessage> messages = chatMessageService.getMessagesAfterJoinedAt(roomId,
                message.senderId())
                .stream()
                .filter(msg -> !msg.getEventType().equals(ChatEventType.ENTER))
                .toList();

        List<ChatMessageReadUpdateDto> response = chatMessageReadService.markAsReadAndUpdate(
                messages, roomId, message.senderId());

        messagingTemplate.convertAndSend("/sub/chatroom/" + roomId + "/read-update", response);
    }

    private void handleChatMessage(Long roomId, ChatMessageSendRequest message) {
        sendChatMessageToRoom(roomId, message);
    }

    private void handleLeave(Long roomId, ChatMessageSendRequest message) {
        boolean leave = chatRoomService.leaveChatRoom(message.senderId(), roomId);

        if (leave) {
            ChatMessage leaveMessage = ChatMessage.createChatMessage(
                    message.senderId(),
                    roomId,
                    message.senderName(),
                    message.senderName() + "님이 퇴장하셨습니다.",
                    ChatMessageType.TEXT.name(),
                    ChatEventType.LEAVE.name()
            );
            sendChatMessageToRoom(roomId, ChatMessageSendRequest.from(leaveMessage));
        }
    }

    private void sendChatMessageToRoom(Long roomId, ChatMessageSendRequest chatMessage) {
        ChatMessage sendMessage = chatMessageService.createChatMessage(roomId, chatMessage);

        Set<String> onlineUserIds = chatRoomOnlineService.getOnlineUserIds(roomId);

        for (String userIdStr : onlineUserIds) {
            Long userId = Long.parseLong(userIdStr);
            chatMessageReadRedisRepository.markAsRead(sendMessage.getId(), userId);
        }

        int totalUserCount = chatRoomService.getTotalUserInRoom(roomId);
        int onlineUserCount = (int) chatRoomOnlineService.getOnlineUserCount(roomId);
        int unreadCount = Math.max(0, totalUserCount - onlineUserCount);
        ChatMessageResponse response = ChatMessageResponse.from(sendMessage, unreadCount);

        messagingTemplate.convertAndSend("/sub/chatroom/" + roomId, response);

        Set<Long> offlineUserIds = userService.getOfflineUserIds(roomId);
        for (Long userId : offlineUserIds) {
            List<ChatMessage> messages = chatMessageService.getMessagesAfterJoinedAt(roomId,
                    userId);

            Long unread = messages.stream()
                    .limit(30)
                    .filter(msg -> !chatMessageReadRedisRepository.isRead(msg.getId(), userId))
                    .count();

            messagingTemplate.convertAndSend(
                    "/sub/user/" + userId + "/unread-summary",
                    UnreadSummaryDto.from(roomId, unread)
            );
        }
    }

}
