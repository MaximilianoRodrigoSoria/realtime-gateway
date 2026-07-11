package com.ar.laboratory.realtimegateway.chat.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ar.laboratory.realtimegateway.chat.application.outbound.port.MessagePublisherPort;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.MessageRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.RoomRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.domain.exception.NotRoomMemberException;
import com.ar.laboratory.realtimegateway.chat.domain.exception.RoomNotFoundException;
import com.ar.laboratory.realtimegateway.chat.domain.model.Message;
import com.ar.laboratory.realtimegateway.chat.domain.model.Room;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostMessageUseCase")
class PostMessageUseCaseTest {

    @Mock private RoomRepositoryPort rooms;
    @Mock private MessageRepositoryPort messages;
    @Mock private MessagePublisherPort publisher;

    private PostMessageUseCase useCase;
    private final UUID roomId = UUID.randomUUID();
    private final UUID sender = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        useCase = new PostMessageUseCase(rooms, messages, publisher);
    }

    @Test
    @DisplayName("miembro publica: persiste y difunde")
    void memberPosts() {
        when(rooms.findById(roomId)).thenReturn(Optional.of(Room.builder().id(roomId).build()));
        when(rooms.isMember(roomId, sender)).thenReturn(true);
        when(messages.save(any(Message.class))).thenAnswer(inv -> inv.getArgument(0));

        Message msg = useCase.execute(roomId, sender, "hola");

        assertThat(msg.getContent()).isEqualTo("hola");
        verify(publisher).publish(any(Message.class));
    }

    @Test
    @DisplayName("no miembro → NotRoomMember y no publica")
    void nonMember() {
        when(rooms.findById(roomId)).thenReturn(Optional.of(Room.builder().id(roomId).build()));
        when(rooms.isMember(roomId, sender)).thenReturn(false);

        assertThatThrownBy(() -> useCase.execute(roomId, sender, "hola"))
                .isInstanceOf(NotRoomMemberException.class);
        verify(messages, never()).save(any());
        verify(publisher, never()).publish(any());
    }

    @Test
    @DisplayName("sala inexistente → RoomNotFound")
    void roomNotFound() {
        when(rooms.findById(roomId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> useCase.execute(roomId, sender, "hola"))
                .isInstanceOf(RoomNotFoundException.class);
    }
}
