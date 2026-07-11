package com.ar.laboratory.realtimegateway.chat.application.usecase;

import com.ar.laboratory.realtimegateway.chat.application.inbound.command.CreateRoomCommand;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.RoomRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.domain.model.MemberRole;
import com.ar.laboratory.realtimegateway.chat.domain.model.Room;
import com.ar.laboratory.realtimegateway.chat.domain.model.RoomMember;
import com.ar.laboratory.realtimegateway.chat.domain.model.RoomType;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** Crea una sala y agrega a su creador como OWNER. POJO puro sin framework. */
@Slf4j
@RequiredArgsConstructor
public class CreateRoomUseCase implements CreateRoomCommand {

    private final RoomRepositoryPort rooms;

    @Override
    public Room execute(String name, RoomType type, UUID creator) {
        Instant now = Instant.now();
        Room room = rooms.save(Room.create(name, type, creator, now));
        rooms.addMember(RoomMember.of(room.getId(), creator, MemberRole.OWNER, now));
        log.info("Sala creada id={} por {}", room.getId(), creator);
        return room;
    }
}
