package com.ar.laboratory.realtimegateway.chat.domain.model;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Membresía de un usuario en una sala. */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoomMember {

    private UUID roomId;
    private UUID userId;
    private MemberRole role;
    private Instant joinedAt;

    public static RoomMember of(UUID roomId, UUID userId, MemberRole role, Instant now) {
        return RoomMember.builder()
                .roomId(roomId)
                .userId(userId)
                .role(role == null ? MemberRole.MEMBER : role)
                .joinedAt(now)
                .build();
    }
}
