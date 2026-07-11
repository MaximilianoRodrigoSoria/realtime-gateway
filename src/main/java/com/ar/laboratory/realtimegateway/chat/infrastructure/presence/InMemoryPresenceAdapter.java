package com.ar.laboratory.realtimegateway.chat.infrastructure.presence;

import com.ar.laboratory.realtimegateway.chat.application.outbound.port.PresencePort;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * Registro de presencia en memoria (single-instance). Para escalar a varias instancias se
 * reemplaza por una implementación sobre Redis con TTL + heartbeat (ver documentación).
 */
@Component
public class InMemoryPresenceAdapter implements PresencePort {

    private final Set<UUID> online = ConcurrentHashMap.newKeySet();

    @Override
    public void markOnline(UUID userId) {
        online.add(userId);
    }

    @Override
    public void markOffline(UUID userId) {
        online.remove(userId);
    }

    @Override
    public boolean isOnline(UUID userId) {
        return online.contains(userId);
    }

    @Override
    public Set<UUID> onlineUserIds() {
        return Set.copyOf(online);
    }
}
