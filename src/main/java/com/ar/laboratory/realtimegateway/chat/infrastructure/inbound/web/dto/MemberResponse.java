package com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Miembro de una sala con su presencia. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private UUID userId;
    private String role;
    private boolean online;
}
