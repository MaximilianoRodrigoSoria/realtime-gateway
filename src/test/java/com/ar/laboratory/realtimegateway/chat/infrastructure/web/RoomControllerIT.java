package com.ar.laboratory.realtimegateway.chat.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Tests de integración de la API REST de salas y mensajes con contexto completo y PostgreSQL real.
 * Cubre creación de sala, membresía, publicación e historial de mensajes.
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.jpa.hibernate.ddl-auto=validate"})
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@DisplayName("RoomController - Integration Tests")
class RoomControllerIT {

    private static final String BASE = "/realtime-gateway/api/v1/rooms";

    @Container @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @LocalServerPort private int port;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    private JsonNode postAs(String path, UUID userId, Map<String, ?> body, int status)
            throws Exception {
        byte[] bytes =
                client.post()
                        .uri(BASE + path)
                        .header("X-User-Id", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body)
                        .exchange()
                        .expectStatus()
                        .isEqualTo(status)
                        .expectBody()
                        .returnResult()
                        .getResponseBodyContent();
        return (bytes == null || bytes.length == 0) ? null : objectMapper.readTree(bytes);
    }

    private UUID createRoom(UUID creator) throws Exception {
        JsonNode node = postAs("", creator, Map.of("name", "general", "type", "PUBLIC"), 201);
        assertThat(node.get("name").asText()).isEqualTo("general");
        return UUID.fromString(node.get("id").asText());
    }

    @Test
    @DisplayName("flujo: crear sala → unirse → postear → historial → miembros")
    void fullFlow() throws Exception {
        UUID creator = UUID.randomUUID();
        UUID member = UUID.randomUUID();
        UUID roomId = createRoom(creator);

        // member se une
        client.post()
                .uri(BASE + "/" + roomId + "/join")
                .header("X-User-Id", member.toString())
                .exchange()
                .expectStatus()
                .isNoContent();

        // ambos postean (son miembros: creator es OWNER, member se unió)
        postAs("/" + roomId + "/messages", creator, Map.of("content", "hola"), 201);
        postAs("/" + roomId + "/messages", member, Map.of("content", "buenas"), 201);

        // historial
        client.get()
                .uri(BASE + "/" + roomId + "/messages")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.content")
                .isArray()
                .jsonPath("$.totalElements")
                .isEqualTo(2);

        // miembros (2)
        client.get()
                .uri(BASE + "/" + roomId + "/members")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.length()")
                .isEqualTo(2);
    }

    @Test
    @DisplayName("postear sin ser miembro → 403")
    void postAsNonMember() throws Exception {
        UUID creator = UUID.randomUUID();
        UUID stranger = UUID.randomUUID();
        UUID roomId = createRoom(creator);

        postAs("/" + roomId + "/messages", stranger, Map.of("content", "intruso"), 403);
    }

    @Test
    @DisplayName("postear en sala inexistente → 404")
    void postInMissingRoom() throws Exception {
        postAs("/" + UUID.randomUUID() + "/messages", UUID.randomUUID(), Map.of("content", "x"), 404);
    }

    @Test
    @DisplayName("crear sala con nombre vacío → 400")
    void createInvalid() throws Exception {
        postAs("", UUID.randomUUID(), Map.of("name", ""), 400);
    }

    @Test
    @DisplayName("listar salas → 200 con estructura de página")
    void listRooms() throws Exception {
        createRoom(UUID.randomUUID());
        client.get()
                .uri(BASE + "?page=0&size=10")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.content")
                .isArray();
    }
}
