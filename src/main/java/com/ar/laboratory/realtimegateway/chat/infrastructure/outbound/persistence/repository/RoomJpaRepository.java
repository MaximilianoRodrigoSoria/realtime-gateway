package com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.repository;

import com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.entity.RoomEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio JPA de salas. */
public interface RoomJpaRepository extends JpaRepository<RoomEntity, UUID> {}
