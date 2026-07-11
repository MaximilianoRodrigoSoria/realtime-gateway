package com.ar.laboratory.realtimegateway.chat.application.outbound.port;

import com.ar.laboratory.realtimegateway.chat.domain.model.Message;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Puerto de salida para mensajes. */
public interface MessageRepositoryPort {
    Message save(Message message);

    Page<Message> findByRoom(UUID roomId, Pageable pageable);
}
