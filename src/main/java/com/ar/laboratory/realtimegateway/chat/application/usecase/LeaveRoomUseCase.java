package com.ar.laboratory.realtimegateway.chat.application.usecase;

import com.ar.laboratory.realtimegateway.chat.application.inbound.command.LeaveRoomCommand;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.RoomRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.domain.exception.RoomNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

/** Saca un usuario de una sala. POJO puro sin framework. */
@RequiredArgsConstructor
public class LeaveRoomUseCase implements LeaveRoomCommand {

    private final RoomRepositoryPort rooms;

    @Override
    public void execute(UUID roomId, UUID userId) {
        rooms.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        rooms.removeMember(roomId, userId);
    }
}
