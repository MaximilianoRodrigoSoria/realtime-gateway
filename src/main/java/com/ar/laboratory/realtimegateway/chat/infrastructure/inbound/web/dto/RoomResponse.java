package com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Vista de una sala. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private UUID id;
    private String name;
    private String type;
    private UUID createdBy;
    private Instant createdAt;
}
