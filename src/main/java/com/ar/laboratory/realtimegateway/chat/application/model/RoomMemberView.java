package com.ar.laboratory.realtimegateway.chat.application.model;

import com.ar.laboratory.realtimegateway.chat.domain.model.MemberRole;
import java.util.UUID;

/** Miembro de una sala con su estado de presencia. */
public record RoomMemberView(UUID userId, MemberRole role, boolean online) {}
