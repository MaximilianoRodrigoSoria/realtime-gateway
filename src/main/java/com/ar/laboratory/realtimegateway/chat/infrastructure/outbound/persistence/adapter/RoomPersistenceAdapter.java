package com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.adapter;

import com.ar.laboratory.realtimegateway.chat.application.outbound.port.RoomRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.domain.model.Room;
import com.ar.laboratory.realtimegateway.chat.domain.model.RoomMember;
import com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.entity.RoomMemberEntity;
import com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.mapper.RoomEntityMapper;
import com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.mapper.RoomMemberEntityMapper;
import com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.repository.RoomJpaRepository;
import com.ar.laboratory.realtimegateway.chat.infrastructure.outbound.persistence.repository.RoomMemberJpaRepository;
import com.ar.laboratory.realtimegateway.shared.infrastructure.exception.InfrastructureException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Adaptador de persistencia de salas y membresías. */
@Component
@RequiredArgsConstructor
public class RoomPersistenceAdapter implements RoomRepositoryPort {

    private final RoomJpaRepository roomRepository;
    private final RoomMemberJpaRepository memberRepository;
    private final RoomEntityMapper roomMapper;
    private final RoomMemberEntityMapper memberMapper;

    @Override
    public Room save(Room room) {
        try {
            return roomMapper.toDomain(roomRepository.save(roomMapper.toEntity(room)));
        } catch (Exception e) {
            throw new InfrastructureException("Error guardando sala", e);
        }
    }

    @Override
    public Optional<Room> findById(UUID id) {
        try {
            return roomRepository.findById(id).map(roomMapper::toDomain);
        } catch (Exception e) {
            throw new InfrastructureException("Error buscando sala", e);
        }
    }

    @Override
    public Page<Room> findAll(Pageable pageable) {
        try {
            return roomRepository.findAll(pageable).map(roomMapper::toDomain);
        } catch (Exception e) {
            throw new InfrastructureException("Error listando salas", e);
        }
    }

    @Override
    public RoomMember addMember(RoomMember member) {
        try {
            RoomMemberEntity entity =
                    RoomMemberEntity.builder()
                            .id(UUID.randomUUID())
                            .roomId(member.getRoomId())
                            .userId(member.getUserId())
                            .role(member.getRole() == null ? null : member.getRole().name())
                            .joinedAt(member.getJoinedAt())
                            .build();
            return memberMapper.toDomain(memberRepository.save(entity));
        } catch (Exception e) {
            throw new InfrastructureException("Error agregando miembro", e);
        }
    }

    @Override
    @Transactional
    public void removeMember(UUID roomId, UUID userId) {
        try {
            memberRepository.deleteByRoomIdAndUserId(roomId, userId);
        } catch (Exception e) {
            throw new InfrastructureException("Error quitando miembro", e);
        }
    }

    @Override
    public boolean isMember(UUID roomId, UUID userId) {
        try {
            return memberRepository.existsByRoomIdAndUserId(roomId, userId);
        } catch (Exception e) {
            throw new InfrastructureException("Error verificando membresía", e);
        }
    }

    @Override
    public List<RoomMember> listMembers(UUID roomId) {
        try {
            return memberRepository.findByRoomId(roomId).stream()
                    .map(memberMapper::toDomain)
                    .toList();
        } catch (Exception e) {
            throw new InfrastructureException("Error listando miembros", e);
        }
    }
}
