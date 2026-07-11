package com.ar.laboratory.realtimegateway.chat.application.inbound.command;

import com.ar.laboratory.realtimegateway.chat.application.model.RoomMemberView;
import java.util.List;
import java.util.UUID;

/** Puerto de entrada: miembros de una sala con su estado de presencia. */
public interface ListMembersCommand {
    List<RoomMemberView> execute(UUID roomId);
}
