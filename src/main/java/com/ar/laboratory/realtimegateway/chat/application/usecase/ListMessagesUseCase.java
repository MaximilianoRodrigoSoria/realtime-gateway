package com.ar.laboratory.realtimegateway.chat.application.usecase;

import com.ar.laboratory.realtimegateway.chat.application.inbound.command.ListMessagesCommand;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.MessageRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.RoomRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.domain.exception.RoomNotFoundException;
import com.ar.laboratory.realtimegateway.chat.domain.model.Message;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Historial de mensajes de una sala. POJO puro sin framework. */
@RequiredArgsConstructor
public class ListMessagesUseCase implements ListMessagesCommand {

    private final RoomRepositoryPort rooms;
    private final MessageRepositoryPort messages;

    @Override
    public Page<Message> execute(UUID roomId, Pageable pageable) {
        rooms.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        return messages.findByRoom(roomId, pageable);
    }
}
