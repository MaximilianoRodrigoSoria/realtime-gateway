package com.ar.laboratory.realtimegateway.chat.domain.model;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Mensaje enviado a una sala. */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private UUID id;
    private UUID roomId;
    private UUID senderId;
    private String content;
    private Instant createdAt;

    public static Message create(UUID roomId, UUID senderId, String content, Instant now) {
        return Message.builder()
                .id(UUID.randomUUID())
                .roomId(roomId)
                .senderId(senderId)
                .content(content)
                .createdAt(now)
                .build();
    }
}
