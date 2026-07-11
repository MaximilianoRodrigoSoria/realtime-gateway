package com.ar.laboratory.realtimegateway.example.application.inbound.command;

import com.ar.laboratory.realtimegateway.example.domain.model.Example;

/** Puerto de entrada para buscar un Example por DNI */
public interface FindExampleByDniCommand {

    Example execute(String dni);
}
