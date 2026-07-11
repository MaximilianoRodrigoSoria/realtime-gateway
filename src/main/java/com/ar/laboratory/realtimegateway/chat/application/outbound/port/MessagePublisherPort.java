package com.ar.laboratory.realtimegateway.chat.application.outbound.port;

import com.ar.laboratory.realtimegateway.chat.domain.model.Message;

/** Puerto de salida para difundir un mensaje en tiempo real a los suscriptores de la sala. */
public interface MessagePublisherPort {
    void publish(Message message);
}
