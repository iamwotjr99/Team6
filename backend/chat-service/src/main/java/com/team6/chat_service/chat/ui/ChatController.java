package com.team6.chat_service.chat.ui;

import com.team6.chat_service.chat.application.ChatMessageReadService;
import com.team6.chat_service.chat.application.ChatMessageService;
import com.team6.chat_service.chat.domain.ChatEventType;
import com.team6.chat_service.chat.domain.ChatMessage;
import com.team6.chat_service.chat.domain.ChatMessageType;
import com.team6.chat_service.chat.infrastructure.MessageBroadcaster;
import com.team6.chat_service.chat.infrastructure.redis.ChatMessageReadRedisRepository;
import com.team6.chat_service.chat.ui.dto.ChatMessageReadUpdateDto;
import com.team6.chat_service.chat.ui.dto.ChatMessageResponse;
import com.team6.chat_service.chat.ui.dto.ChatMessageSendRequest;
import com.team6.chat_service.chat.ui.dto.UnreadSummaryDto;
import com.team6.chat_service.chatroom.application.ChatRoomOnlineService;
import com.team6.chat_service.chatroom.application.ChatRoomService;
import com.team6.chat_service.chatroom.domain.ChatRoomUser;
import com.team6.chat_service.chatroom.domain.repository.ChatRoomUserRepository;
import com.team6.chat_service.global.config.redis.RedisPublisher;
import com.team6.chat_service.global.config.redis.dto.ChatMessageBroadcastDto;
import com.team6.chat_service.user.application.UserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageReadService chatMessageReadService;
    private final ChatMessageReadRedisRepository chatMessageReadRedisRepository;
    private final ChatRoomOnlineService chatRoomOnlineService;
    private final UserService userService;
    private final RedisPublisher redisPublisher;
    private final ChannelTopic chatTopic;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final MessageBroadcaster messageBroadcaster;

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

    @MessageMapping("/chatroom/{roomId}/exit")
    public void exitMessage(@DestinationVariable Long roomId, @Payload ChatMessageSendRequest message) {
        log.info("Exit message: {}", message);

        List<ChatMessage> messages = chatMessageService.getMessagesAfterJoinedAt(roomId, message.senderId());

        List<ChatMessage> actuallyRead = messages.stream()
                .filter(msg -> chatMessageReadRedisRepository.isRead(msg.getId(), message.senderId()))
                .toList();

        chatMessageReadService.markAsReadAndUpdate(actuallyRead, roomId, message.senderId());
    }


    private void handleEnter(Long roomId, ChatMessageSendRequest message) {
        boolean alreadyEntered = chatRoomService.enterChatRoom(message.senderId(), roomId);

        if(!alreadyEntered) {
            // int totalUserInRoom = chatRoomService.getTotalUserInRoom(roomId);
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

        ChatMessageBroadcastDto broadcastMessage = ChatMessageBroadcastDto.from(
                "/sub/chatroom/" + roomId + "/read-update", response);

        // messagingTemplate.convertAndSend("/sub/chatroom/" + roomId + "/read-update", response);
        messageBroadcaster.broadcast(chatTopic.getTopic(), broadcastMessage);
    }

    private void handleChatMessage(Long roomId, ChatMessageSendRequest message) {
        sendChatMessageToRoom(roomId, message);
    }

    private void handleLeave(Long roomId, ChatMessageSendRequest message) {
        boolean leave = chatRoomService.leaveChatRoom(message.senderId(), roomId);

        if (leave) {
            // int totalUserInRoom = chatRoomService.getTotalUserInRoom(roomId);
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

        List<ChatMessage> messages = chatMessageService.getMessagesAfterJoinedAt(roomId,
                message.senderId());

        List<ChatMessage> actuallyRead = messages.stream()
                .filter(msg -> chatMessageReadRedisRepository.isRead(msg.getId(),
                        message.senderId()))
                .toList();

        chatMessageReadService.markAsReadAndUpdate(actuallyRead, roomId, message.senderId());
    }

    private void sendChatMessageToRoom(Long roomId, ChatMessageSendRequest
            chatMessage) {
        ChatMessage savedMessage = chatMessageService.createChatMessage(roomId, chatMessage);

        Set<String> onlineUserIds = chatRoomOnlineService.getOnlineUserIds(roomId);

        for (String userIdStr : onlineUserIds) {
            Long userId = Long.parseLong(userIdStr);
            chatMessageReadRedisRepository.markAsRead(savedMessage.getId(), userId);
        }

        List<ChatRoomUser> participants = chatRoomUserRepository.findByRoomId(roomId);

        for (ChatRoomUser participant : participants) {
            boolean isRead = chatMessageReadRedisRepository.isRead(savedMessage.getId(), participant.getUserId());
            log.info("User {} joinedAt={} / msg.createdAt={} / isRead={}",
                    participant.getUserId(), participant.getJoinedAt(), savedMessage.getCreatedAt(), isRead);
        }


        int unreadCount = Math.max(0, chatMessageService.calculateUnreadCount(savedMessage, participants));
        ChatMessageResponse response = ChatMessageResponse.from(savedMessage, unreadCount);

        ChatMessageBroadcastDto broadcastMessage = ChatMessageBroadcastDto.from("/sub/chatroom/" + roomId,
                response);

        // messagingTemplate.convertAndSend("/sub/chatroom/" + roomId, response);
        messageBroadcaster.broadcast(chatTopic.getTopic(), broadcastMessage);

        Set<Long> offlineUserIds = userService.getOfflineUserIds(roomId);
        for (Long userId : offlineUserIds) {
            List<ChatMessage> messages = chatMessageService.getMessagesAfterJoinedAt(roomId,
                    userId);

            Long unread = messages.stream()
                    .limit(50)
                    .filter(msg -> !chatMessageReadRedisRepository.isRead(msg.getId(), userId))
                    .count();

            String preview = savedMessage.getMessageType() == ChatMessageType.PICTURE ? "사진" : savedMessage.getContent();

            ChatMessageBroadcastDto broadcastMessageUnreadSummary = ChatMessageBroadcastDto.from(
                    "/sub/user/" + userId + "/unread-summary",
                    UnreadSummaryDto.from(roomId, preview, savedMessage.getCreatedAt().toString(), unread));

//            messagingTemplate.convertAndSend(
//                    "/sub/user/" + userId + "/unread-summary",
//                    UnreadSummaryDto.from(roomId, unread)
//            );

            messageBroadcaster.broadcast(chatTopic.getTopic(), broadcastMessageUnreadSummary);
        }
    }

}
