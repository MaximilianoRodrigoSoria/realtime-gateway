package com.ar.laboratory.realtimegateway.chat.application.outbound.port;

import java.util.Set;
import java.util.UUID;

/** Puerto de salida para el registro de presencia (usuarios conectados). */
public interface PresencePort {
    void markOnline(UUID userId);

    void markOffline(UUID userId);

    boolean isOnline(UUID userId);

    Set<UUID> onlineUserIds();
}
