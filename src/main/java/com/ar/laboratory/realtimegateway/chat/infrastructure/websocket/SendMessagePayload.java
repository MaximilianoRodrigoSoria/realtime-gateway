package com.ar.laboratory.realtimegateway.chat.infrastructure.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Payload STOMP para enviar un mensaje a una sala. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessagePayload {
    private String content;
}
