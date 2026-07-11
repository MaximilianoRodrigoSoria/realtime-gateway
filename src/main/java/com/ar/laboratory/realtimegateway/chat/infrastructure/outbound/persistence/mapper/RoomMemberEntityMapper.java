package com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.mapper;

import com.ar.laboratory.realtimegateway.chat.domain.model.MemberRole;
import com.ar.laboratory.realtimegateway.chat.domain.model.RoomMember;
import com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.entity.RoomMemberEntity;
import org.springframework.stereotype.Component;

/** Conversión RoomMemberEntity → RoomMember (dominio). */
@Component
public class RoomMemberEntityMapper {

    public RoomMember toDomain(RoomMemberEntity e) {
        if (e == null) {
            return null;
        }
        return RoomMember.builder()
                .roomId(e.getRoomId())
                .userId(e.getUserId())
                .role(e.getRole() == null ? null : MemberRole.valueOf(e.getRole()))
                .joinedAt(e.getJoinedAt())
                .build();
    }
}
