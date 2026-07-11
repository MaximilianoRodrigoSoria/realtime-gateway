package com.ar.laboratory.realtimegateway.chat.application.inbound.command;

import com.ar.laboratory.realtimegateway.chat.domain.model.Room;
import com.ar.laboratory.realtimegateway.chat.domain.model.RoomType;
import java.util.UUID;

/** Puerto de entrada: crear una sala. */
public interface CreateRoomCommand {
    Room execute(String name, RoomType type, UUID creator);
}
