package com.ar.laboratory.realtimegateway.chat.domain.exception;

/** El usuario no es miembro de la sala. */
public class NotRoomMemberException extends RuntimeException {
    public NotRoomMemberException(String message) {
        super(message);
    }
}
