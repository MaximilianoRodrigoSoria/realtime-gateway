package com.ar.laboratory.realtimegateway.chat.application.outbound.port;

import com.ar.laboratory.realtimegateway.chat.domain.model.Room;
import com.ar.laboratory.realtimegateway.chat.domain.model.RoomMember;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Puerto de salida para salas y membresías. */
public interface RoomRepositoryPort {
    Room save(Room room);

    Optional<Room> findById(UUID id);

    Page<Room> findAll(Pageable pageable);

    RoomMember addMember(RoomMember member);

    void removeMember(UUID roomId, UUID userId);

    boolean isMember(UUID roomId, UUID userId);

    List<RoomMember> listMembers(UUID roomId);
}
