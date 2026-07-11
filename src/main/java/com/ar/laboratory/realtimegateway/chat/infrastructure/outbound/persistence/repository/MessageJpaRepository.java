package com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.repository;

import com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.entity.MessageEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio JPA de mensajes. */
public interface MessageJpaRepository extends JpaRepository<MessageEntity, UUID> {
    Page<MessageEntity> findByRoomId(UUID roomId, Pageable pageable);
}
