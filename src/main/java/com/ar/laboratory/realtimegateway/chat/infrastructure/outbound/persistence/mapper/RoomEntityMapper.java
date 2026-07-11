package com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.mapper;

import com.ar.laboratory.realtimegateway.chat.domain.model.Room;
import com.ar.laboratory.realtimegateway.chat.domain.model.RoomType;
import com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.entity.RoomEntity;
import org.springframework.stereotype.Component;

/** Conversión RoomEntity ↔ Room. */
@Component
public class RoomEntityMapper {

    public Room toDomain(RoomEntity e) {
        if (e == null) {
            return null;
        }
        return Room.builder()
                .id(e.getId())
                .name(e.getName())
                .type(e.getType() == null ? null : RoomType.valueOf(e.getType()))
                .createdBy(e.getCreatedBy())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public RoomEntity toEntity(Room r) {
        return RoomEntity.builder()
                .id(r.getId())
                .name(r.getName())
                .type(r.getType() == null ? null : r.getType().name())
                .createdBy(r.getCreatedBy())
                .createdAt(r.getCreatedAt())
                .build();
    }
}
