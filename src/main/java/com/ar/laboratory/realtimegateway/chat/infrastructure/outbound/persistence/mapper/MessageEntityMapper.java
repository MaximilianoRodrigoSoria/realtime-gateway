package com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.mapper;

import com.ar.laboratory.realtimegateway.chat.domain.model.Message;
import com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.entity.MessageEntity;
import org.springframework.stereotype.Component;

/** Conversión MessageEntity ↔ Message. */
@Component
public class MessageEntityMapper {

    public Message toDomain(MessageEntity e) {
        if (e == null) {
            return null;
        }
        return Message.builder()
                .id(e.getId())
                .roomId(e.getRoomId())
                .senderId(e.getSenderId())
                .content(e.getContent())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public MessageEntity toEntity(Message m) {
        return MessageEntity.builder()
                .id(m.getId())
                .roomId(m.getRoomId())
                .senderId(m.getSenderId())
                .content(m.getContent())
                .createdAt(m.getCreatedAt())
                .build();
    }
}
