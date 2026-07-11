package com.ar.laboratory.realtimegateway.chat.application.inbound.command;

import com.ar.laboratory.realtimegateway.chat.domain.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Puerto de entrada: listar salas. */
public interface ListRoomsCommand {
    Page<Room> execute(Pageable pageable);
}
