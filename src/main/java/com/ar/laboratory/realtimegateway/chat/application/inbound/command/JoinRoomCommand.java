package com.ar.laboratory.realtimegateway.chat.application.inbound.command;

import java.util.UUID;

/** Puerto de entrada: unirse a una sala. */
public interface JoinRoomCommand {
    void execute(UUID roomId, UUID userId);
}
