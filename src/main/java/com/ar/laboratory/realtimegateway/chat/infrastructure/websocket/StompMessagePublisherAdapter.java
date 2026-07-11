package com.ar.laboratory.realtimegateway.chat.infrastructure.websocket;

import com.ar.laboratory.realtimegateway.chat.application.outbound.port.MessagePublisherPort;
import com.ar.laboratory.realtimegateway.chat.domain.model.Message;
import com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/** Difunde el mensaje a los suscriptores de {@code /topic/rooms/{roomId}} vía STOMP. */
@Component
@RequiredArgsConstructor
public class StompMessagePublisherAdapter implements MessagePublisherPort {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void publish(Message message) {
        MessageResponse payload =
                MessageResponse.builder()
                        .id(message.getId())
                        .roomId(message.getRoomId())
                        .senderId(message.getSenderId())
                        .content(message.getContent())
                        .createdAt(message.getCreatedAt())
                        .build();
        messagingTemplate.convertAndSend("/topic/rooms/" + message.getRoomId(), payload);
    }
}
