package com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.controller;

import com.ar.laboratory.realtimegateway.chat.application.inbound.command.CreateRoomCommand;
import com.ar.laboratory.realtimegateway.chat.application.inbound.command.JoinRoomCommand;
import com.ar.laboratory.realtimegateway.chat.application.inbound.command.LeaveRoomCommand;
import com.ar.laboratory.realtimegateway.chat.application.inbound.command.ListMembersCommand;
import com.ar.laboratory.realtimegateway.chat.application.inbound.command.ListMessagesCommand;
import com.ar.laboratory.realtimegateway.chat.application.inbound.command.ListRoomsCommand;
import com.ar.laboratory.realtimegateway.chat.application.inbound.command.PostMessageCommand;
import com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.dto.CreateRoomRequest;
import com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.dto.MemberResponse;
import com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.dto.MessageResponse;
import com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.dto.PostMessageRequest;
import com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.dto.RoomResponse;
import com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.mapper.ChatDtoMapper;
import com.ar.laboratory.realtimegateway.shared.infrastructure.web.dto.PageResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * API REST de salas y mensajes. La identidad del usuario llega en la cabecera {@code X-User-Id}
 * (en un despliegue real se deriva del JWT validado en el gateway).
 */
@Tag(name = "Rooms", description = "Salas, membresías y mensajes")
@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@RateLimiter(name = "rooms-api")
public class RoomController {

    private static final String USER_HEADER = "X-User-Id";

    private final CreateRoomCommand createRoomCommand;
    private final ListRoomsCommand listRoomsCommand;
    private final JoinRoomCommand joinRoomCommand;
    private final LeaveRoomCommand leaveRoomCommand;
    private final ListMembersCommand listMembersCommand;
    private final PostMessageCommand postMessageCommand;
    private final ListMessagesCommand listMessagesCommand;
    private final ChatDtoMapper mapper;

    @PostMapping
    public ResponseEntity<RoomResponse> create(
            @RequestHeader(USER_HEADER) UUID userId,
            @Valid @RequestBody CreateRoomRequest request) {
        var room = createRoomCommand.execute(request.getName(), request.getType(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toRoomResponse(room));
    }

    @GetMapping
    public ResponseEntity<PageResponse<RoomResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var result =
                listRoomsCommand
                        .execute(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")))
                        .map(mapper::toRoomResponse);
        return ResponseEntity.ok(PageResponse.of(result));
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<Void> join(
            @PathVariable UUID id, @RequestHeader(USER_HEADER) UUID userId) {
        joinRoomCommand.execute(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<Void> leave(
            @PathVariable UUID id, @RequestHeader(USER_HEADER) UUID userId) {
        leaveRoomCommand.execute(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<MemberResponse>> members(@PathVariable UUID id) {
        List<MemberResponse> members =
                listMembersCommand.execute(id).stream().map(mapper::toMemberResponse).toList();
        return ResponseEntity.ok(members);
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity<MessageResponse> postMessage(
            @PathVariable UUID id,
            @RequestHeader(USER_HEADER) UUID userId,
            @Valid @RequestBody PostMessageRequest request) {
        var message = postMessageCommand.execute(id, userId, request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toMessageResponse(message));
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<PageResponse<MessageResponse>> messages(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size) {
        var result =
                listMessagesCommand
                        .execute(id, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")))
                        .map(mapper::toMessageResponse);
        return ResponseEntity.ok(PageResponse.of(result));
    }
}
