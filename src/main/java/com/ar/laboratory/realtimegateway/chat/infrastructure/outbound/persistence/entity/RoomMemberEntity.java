package com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/** Entidad JPA de membresía de sala (tabla {@code app.room_members}). */
@Entity
@Table(
        name = "room_members",
        schema = "app",
        uniqueConstraints =
                @UniqueConstraint(
                        name = "uk_room_member",
                        columnNames = {"room_id", "user_id"}))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomMemberEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "room_id", nullable = false)
    private UUID roomId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;
}
