package com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.repository;

import com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.entity.RoomMemberEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio JPA de membresías. */
public interface RoomMemberJpaRepository extends JpaRepository<RoomMemberEntity, UUID> {
    boolean existsByRoomIdAndUserId(UUID roomId, UUID userId);

    long deleteByRoomIdAndUserId(UUID roomId, UUID userId);

    List<RoomMemberEntity> findByRoomId(UUID roomId);
}
