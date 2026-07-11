package com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.mapper;

import com.ar.laboratory.realtimegateway.chat.application.model.RoomMemberView;
import com.ar.laboratory.realtimegateway.chat.domain.model.Message;
import com.ar.laboratory.realtimegateway.chat.domain.model.Room;
import com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.dto.MemberResponse;
import com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.dto.MessageResponse;
import com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.dto.RoomResponse;
import org.springframework.stereotype.Component;

/** Conversión de dominio a DTOs de la API de chat. */
@Component
public class ChatDtoMapper {

    public RoomResponse toRoomResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .type(room.getType() == null ? null : room.getType().name())
                .createdBy(room.getCreatedBy())
                .createdAt(room.getCreatedAt())
                .build();
    }

    public MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .roomId(message.getRoomId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }

    public MemberResponse toMemberResponse(RoomMemberView view) {
        return MemberResponse.builder()
                .userId(view.userId())
                .role(view.role() == null ? null : view.role().name())
                .online(view.online())
                .build();
    }
}
