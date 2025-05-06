package com.team6.chat_service.global.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class StompBrokerConfig implements WebSocketMessageBrokerConfigurer {

    private final CustomHandshakeInterceptor customHandshakeInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .addInterceptors(customHandshakeInterceptor)
                .setAllowedOrigins(
                        "http://localhost:5500",
                        "http://127.0.0.1:5500",
                        "http://127.0.0.1:3000",
                        "http://3.38.153.246:8080",
                        "http://3.38.153.246:3000",
                        "http://localhost:3000",
                        "http://localhost:63342")
                .withSockJS();
    }
}
