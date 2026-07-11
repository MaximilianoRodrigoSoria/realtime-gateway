package com.ar.laboratory.realtimegateway.chat.infrastructure.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

/**
 * Autentica el handshake STOMP. En el CONNECT lee el identificador de usuario y lo fija como
 * principal de la sesión. En un despliegue real validaría el JWT (mismo secreto que el gateway);
 * aquí el core acepta la cabecera nativa {@code userId} para asociar la conexión a un usuario.
 */
@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String userId = accessor.getFirstNativeHeader("userId");
            if (userId != null && !userId.isBlank()) {
                final String uid = userId;
                accessor.setUser(() -> uid);
            }
        }
        return message;
    }
}
