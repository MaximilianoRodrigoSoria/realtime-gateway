package com.ar.laboratory.realtimegateway.example.application.inbound.command;

import com.ar.laboratory.realtimegateway.example.domain.model.Example;

/** Puerto de entrada para crear un Example */
public interface CreateExampleCommand {

    Example execute(Example example);
}
