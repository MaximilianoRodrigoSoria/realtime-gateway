package com.ar.laboratory.realtimegateway.chat.application.inbound.command;

import java.util.UUID;

/** Puerto de entrada: salir de una sala. */
public interface LeaveRoomCommand {
    void execute(UUID roomId, UUID userId);
}
