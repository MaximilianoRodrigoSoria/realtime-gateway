package com.ar.laboratory.realtimegateway.chat.infrastructure.websocket;

import com.ar.laboratory.realtimegateway.chat.application.inbound.command.PostMessageCommand;
import java.security.Principal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * Controlador STOMP: recibe mensajes en {@code /app/rooms/{roomId}/send}, los persiste y difunde
 * (el caso de uso publica en {@code /topic/rooms/{roomId}} a través del puerto de publicación).
 */
@Controller
@RequiredArgsConstructor
public class StompMessageController {

    private final PostMessageCommand postMessageCommand;

    @MessageMapping("/rooms/{roomId}/send")
    public void send(
            @DestinationVariable UUID roomId, SendMessagePayload payload, Principal principal) {
        UUID senderId = UUID.fromString(principal.getName());
        postMessageCommand.execute(roomId, senderId, payload.getContent());
    }
}
