package com.ar.laboratory.realtimegateway.chat.application.usecase;

import com.ar.laboratory.realtimegateway.chat.application.inbound.command.PostMessageCommand;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.MessagePublisherPort;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.MessageRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.RoomRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.domain.exception.NotRoomMemberException;
import com.ar.laboratory.realtimegateway.chat.domain.exception.RoomNotFoundException;
import com.ar.laboratory.realtimegateway.chat.domain.model.Message;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** Publica un mensaje en una sala: valida membresía, persiste y difunde en tiempo real. */
@Slf4j
@RequiredArgsConstructor
public class PostMessageUseCase implements PostMessageCommand {

    private final RoomRepositoryPort rooms;
    private final MessageRepositoryPort messages;
    private final MessagePublisherPort publisher;

    @Override
    public Message execute(UUID roomId, UUID senderId, String content) {
        rooms.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        if (!rooms.isMember(roomId, senderId)) {
            throw new NotRoomMemberException(
                    "El usuario " + senderId + " no es miembro de la sala " + roomId);
        }
        Message saved = messages.save(Message.create(roomId, senderId, content, Instant.now()));
        publisher.publish(saved);
        log.debug("Mensaje {} publicado en sala {}", saved.getId(), roomId);
        return saved;
    }
}
