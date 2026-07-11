package com.ar.laboratory.realtimegateway.chat.application.inbound.command;

import com.ar.laboratory.realtimegateway.chat.domain.model.Message;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Puerto de entrada: historial de mensajes de una sala. */
public interface ListMessagesCommand {
    Page<Message> execute(UUID roomId, Pageable pageable);
}
