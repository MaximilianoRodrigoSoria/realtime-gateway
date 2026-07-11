package com.ar.laboratory.realtimegateway.chat.application.usecase;

import com.ar.laboratory.realtimegateway.chat.application.inbound.command.ListRoomsCommand;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.RoomRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.domain.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Lista salas paginadas. POJO puro sin framework. */
@RequiredArgsConstructor
public class ListRoomsUseCase implements ListRoomsCommand {

    private final RoomRepositoryPort rooms;

    @Override
    public Page<Room> execute(Pageable pageable) {
        return rooms.findAll(pageable);
    }
}
