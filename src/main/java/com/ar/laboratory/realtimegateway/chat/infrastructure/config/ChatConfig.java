package com.ar.laboratory.realtimegateway.chat.infrastructure.config;

import com.ar.laboratory.realtimegateway.chat.application.inbound.command.CreateRoomCommand;
import com.ar.laboratory.realtimegateway.chat.application.inbound.command.JoinRoomCommand;
import com.ar.laboratory.realtimegateway.chat.application.inbound.command.LeaveRoomCommand;
import com.ar.laboratory.realtimegateway.chat.application.inbound.command.ListMembersCommand;
import com.ar.laboratory.realtimegateway.chat.application.inbound.command.ListMessagesCommand;
import com.ar.laboratory.realtimegateway.chat.application.inbound.command.ListRoomsCommand;
import com.ar.laboratory.realtimegateway.chat.application.inbound.command.PostMessageCommand;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.MessagePublisherPort;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.MessageRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.PresencePort;
import com.ar.laboratory.realtimegateway.chat.application.outbound.port.RoomRepositoryPort;
import com.ar.laboratory.realtimegateway.chat.application.usecase.CreateRoomUseCase;
import com.ar.laboratory.realtimegateway.chat.application.usecase.JoinRoomUseCase;
import com.ar.laboratory.realtimegateway.chat.application.usecase.LeaveRoomUseCase;
import com.ar.laboratory.realtimegateway.chat.application.usecase.ListMembersUseCase;
import com.ar.laboratory.realtimegateway.chat.application.usecase.ListMessagesUseCase;
import com.ar.laboratory.realtimegateway.chat.application.usecase.ListRoomsUseCase;
import com.ar.laboratory.realtimegateway.chat.application.usecase.PostMessageUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Wiring de los casos de uso del feature chat (POJOs) con sus puertos. */
@Configuration
public class ChatConfig {

    @Bean
    public CreateRoomCommand createRoomCommand(RoomRepositoryPort rooms) {
        return new CreateRoomUseCase(rooms);
    }

    @Bean
    public ListRoomsCommand listRoomsCommand(RoomRepositoryPort rooms) {
        return new ListRoomsUseCase(rooms);
    }

    @Bean
    public JoinRoomCommand joinRoomCommand(RoomRepositoryPort rooms) {
        return new JoinRoomUseCase(rooms);
    }

    @Bean
    public LeaveRoomCommand leaveRoomCommand(RoomRepositoryPort rooms) {
        return new LeaveRoomUseCase(rooms);
    }

    @Bean
    public ListMembersCommand listMembersCommand(RoomRepositoryPort rooms, PresencePort presence) {
        return new ListMembersUseCase(rooms, presence);
    }

    @Bean
    public PostMessageCommand postMessageCommand(
            RoomRepositoryPort rooms,
            MessageRepositoryPort messages,
            MessagePublisherPort publisher) {
        return new PostMessageUseCase(rooms, messages, publisher);
    }

    @Bean
    public ListMessagesCommand listMessagesCommand(
            RoomRepositoryPort rooms, MessageRepositoryPort messages) {
        return new ListMessagesUseCase(rooms, messages);
    }
}
