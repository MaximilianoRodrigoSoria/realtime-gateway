package com.ar.laboratory.realtimegateway.chat.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ar.laboratory.realtimegateway.chat.application.model.RoomMemberView;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.PresencePort;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.RoomRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.domain.exception.RoomNotFoundException;
import com.ar.laboratory.realtimegateway.chat.domain.model.MemberRole;
import com.ar.laboratory.realtimegateway.chat.domain.model.Room;
import com.ar.laboratory.realtimegateway.chat.domain.model.RoomMember;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Join / Leave / ListMembers / ListMessages")
class RoomMembershipUseCasesTest {

    @Mock private RoomRepositoryPort rooms;
    @Mock private PresencePort presence;

    private final UUID roomId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();

    @Test
    @DisplayName("join agrega al usuario si no es miembro")
    void joinAddsMember() {
        when(rooms.findById(roomId)).thenReturn(Optional.of(Room.builder().id(roomId).build()));
        when(rooms.isMember(roomId, userId)).thenReturn(false);
        new JoinRoomUseCase(rooms).execute(roomId, userId);
        verify(rooms).addMember(any(RoomMember.class));
    }

    @Test
    @DisplayName("join es idempotente si ya es miembro")
    void joinIdempotent() {
        when(rooms.findById(roomId)).thenReturn(Optional.of(Room.builder().id(roomId).build()));
        when(rooms.isMember(roomId, userId)).thenReturn(true);
        new JoinRoomUseCase(rooms).execute(roomId, userId);
        verify(rooms, never()).addMember(any());
    }

    @Test
    @DisplayName("join en sala inexistente → RoomNotFound")
    void joinRoomNotFound() {
        when(rooms.findById(roomId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> new JoinRoomUseCase(rooms).execute(roomId, userId))
                .isInstanceOf(RoomNotFoundException.class);
    }

    @Test
    @DisplayName("leave quita al miembro")
    void leaveRemoves() {
        when(rooms.findById(roomId)).thenReturn(Optional.of(Room.builder().id(roomId).build()));
        new LeaveRoomUseCase(rooms).execute(roomId, userId);
        verify(rooms).removeMember(roomId, userId);
    }

    @Test
    @DisplayName("listMembers anota la presencia")
    void listMembersWithPresence() {
        RoomMember m = RoomMember.of(roomId, userId, MemberRole.OWNER, Instant.now());
        when(rooms.findById(roomId)).thenReturn(Optional.of(Room.builder().id(roomId).build()));
        when(rooms.listMembers(roomId)).thenReturn(List.of(m));
        when(presence.isOnline(userId)).thenReturn(true);

        List<RoomMemberView> views = new ListMembersUseCase(rooms, presence).execute(roomId);
        assertThat(views).singleElement().satisfies(v -> {
            assertThat(v.userId()).isEqualTo(userId);
            assertThat(v.online()).isTrue();
        });
    }
}
