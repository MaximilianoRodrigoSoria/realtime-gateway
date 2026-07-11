package com.ar.laboratory.realtimegateway.chat.application.usecase;

import com.ar.laboratory.realtimegateway.chat.application.inbound.command.ListMembersCommand;
import com.ar.laboratory.realtimegateway.chat.application.model.RoomMemberView;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.PresencePort;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.RoomRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.domain.exception.RoomNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

/** Miembros de una sala anotados con su estado de presencia. POJO puro sin framework. */
@RequiredArgsConstructor
public class ListMembersUseCase implements ListMembersCommand {

    private final RoomRepositoryPort rooms;
    private final PresencePort presence;

    @Override
    public List<RoomMemberView> execute(UUID roomId) {
        rooms.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        return rooms.listMembers(roomId).stream()
                .map(
                        m ->
                                new RoomMemberView(
                                        m.getUserId(),
                                        m.getRole(),
                                        presence.isOnline(m.getUserId())))
                .toList();
    }
}
