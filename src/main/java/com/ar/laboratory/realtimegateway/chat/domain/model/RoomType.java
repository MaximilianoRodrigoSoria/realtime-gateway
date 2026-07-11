package com.ar.laboratory.realtimegateway.chat.domain.model;

/** Tipo de sala. */
public enum RoomType {
    /** Cualquiera puede unirse. */
    PUBLIC,
    /** Requiere invitación / membresía explícita. */
    PRIVATE,
    /** Conversación 1:1. */
    DIRECT
}
