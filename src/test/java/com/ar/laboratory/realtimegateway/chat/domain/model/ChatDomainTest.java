package com.ar.laboratory.realtimegateway.chat.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Dominio de chat")
class ChatDomainTest {

    private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");

    @Test
    @DisplayName("Room.create asigna id, tipo por defecto PUBLIC y owner")
    void createRoom() {
        UUID owner = UUID.randomUUID();
        Room room = Room.create("general", null, owner, NOW);
        assertThat(room.getId()).isNotNull();
        assertThat(room.getType()).isEqualTo(RoomType.PUBLIC);
        assertThat(room.isPublic()).isTrue();
        assertThat(room.getCreatedBy()).isEqualTo(owner);
    }

    @Test
    @DisplayName("RoomMember.of usa MEMBER por defecto")
    void createMember() {
        RoomMember m = RoomMember.of(UUID.randomUUID(), UUID.randomUUID(), null, NOW);
        assertThat(m.getRole()).isEqualTo(MemberRole.MEMBER);
    }

    @Test
    @DisplayName("Message.create asigna id y timestamp")
    void createMessage() {
        Message msg = Message.create(UUID.randomUUID(), UUID.randomUUID(), "hola", NOW);
        assertThat(msg.getId()).isNotNull();
        assertThat(msg.getContent()).isEqualTo("hola");
        assertThat(msg.getCreatedAt()).isEqualTo(NOW);
    }
}
