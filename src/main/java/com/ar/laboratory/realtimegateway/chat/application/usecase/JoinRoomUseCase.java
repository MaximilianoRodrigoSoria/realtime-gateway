package com.ar.laboratory.realtimegateway.chat.application.usecase;

import com.ar.laboratory.realtimegateway.chat.application.inbound.command.JoinRoomCommand;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.RoomRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.domain.exception.RoomNotFoundException;
import com.ar.laboratory.realtimegateway.chat.domain.model.MemberRole;
import com.ar.laboratory.realtimegateway.chat.domain.model.RoomMember;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

/** Une un usuario a una sala (idempotente). POJO puro sin framework. */
@RequiredArgsConstructor
public class JoinRoomUseCase implements JoinRoomCommand {

    private final RoomRepositoryPort rooms;

    @Override
    public void execute(UUID roomId, UUID userId) {
        rooms.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        if (!rooms.isMember(roomId, userId)) {
            rooms.addMember(RoomMember.of(roomId, userId, MemberRole.MEMBER, Instant.now()));
        }
    }
}
