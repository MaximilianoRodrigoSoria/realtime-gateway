package com.ar.laboratory.realtimegateway.chat.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ar.laboratory.realtimegateway.chat.application.outbound.port.RoomRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.domain.model.MemberRole;
import com.ar.laboratory.realtimegateway.chat.domain.model.Room;
import com.ar.laboratory.realtimegateway.chat.domain.model.RoomMember;
import com.ar.laboratory.realtimegateway.chat.domain.model.RoomType;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateRoomUseCase")
class CreateRoomUseCaseTest {

    @Mock private RoomRepositoryPort rooms;

    @Test
    @DisplayName("crea la sala y agrega al creador como OWNER")
    void createsRoomAndOwner() {
        UUID creator = UUID.randomUUID();
        when(rooms.save(any(Room.class))).thenAnswer(inv -> inv.getArgument(0));

        Room room = new CreateRoomUseCase(rooms).execute("general", RoomType.PUBLIC, creator);

        assertThat(room.getName()).isEqualTo("general");
        ArgumentCaptor<RoomMember> captor = ArgumentCaptor.forClass(RoomMember.class);
        verify(rooms).addMember(captor.capture());
        assertThat(captor.getValue().getRole()).isEqualTo(MemberRole.OWNER);
        assertThat(captor.getValue().getUserId()).isEqualTo(creator);
    }
}
