package com.ar.laboratory.realtimegateway.chat.application.inbound.command;

import com.ar.laboratory.realtimegateway.chat.domain.model.Message;
import java.util.UUID;

/** Puerto de entrada: publicar un mensaje en una sala (persiste y difunde). */
public interface PostMessageCommand {
    Message execute(UUID roomId, UUID senderId, String content);
}
