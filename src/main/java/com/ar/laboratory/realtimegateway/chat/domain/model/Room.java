package com.ar.laboratory.realtimegateway.chat.domain.model;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Sala de conversación. */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    private UUID id;
    private String name;
    private RoomType type;
    private UUID createdBy;
    private Instant createdAt;

    /** Crea una sala nueva del tipo dado, propiedad de {@code createdBy}. */
    public static Room create(String name, RoomType type, UUID createdBy, Instant now) {
        return Room.builder()
                .id(UUID.randomUUID())
                .name(name)
                .type(type == null ? RoomType.PUBLIC : type)
                .createdBy(createdBy)
                .createdAt(now)
                .build();
    }

    public boolean isPublic() {
        return type == RoomType.PUBLIC;
    }
}
