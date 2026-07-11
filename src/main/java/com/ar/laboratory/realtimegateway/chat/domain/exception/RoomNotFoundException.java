package com.ar.laboratory.realtimegateway.chat.domain.exception;

import java.util.UUID;

/** No se encontró la sala. */
public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(UUID id) {
        super("Sala no encontrada: " + id);
    }
}
