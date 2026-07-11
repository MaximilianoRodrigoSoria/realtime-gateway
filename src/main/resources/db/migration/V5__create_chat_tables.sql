-- ─────────────────────────────────────────────────────────────────────────────
-- Salas, membresías y mensajes del gateway de tiempo real.
-- ─────────────────────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS app.rooms (
    id         UUID         PRIMARY KEY,
    name       VARCHAR(150) NOT NULL,
    type       VARCHAR(20)  NOT NULL,
    created_by UUID         NOT NULL,
    created_at TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS app.room_members (
    id        UUID        PRIMARY KEY,
    room_id   UUID        NOT NULL REFERENCES app.rooms(id) ON DELETE CASCADE,
    user_id   UUID        NOT NULL,
    role      VARCHAR(20) NOT NULL,
    joined_at TIMESTAMP   NOT NULL,
    CONSTRAINT uk_room_member UNIQUE (room_id, user_id)
);

CREATE TABLE IF NOT EXISTS app.messages (
    id         UUID      PRIMARY KEY,
    room_id    UUID      NOT NULL REFERENCES app.rooms(id) ON DELETE CASCADE,
    sender_id  UUID      NOT NULL,
    content    TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_messages_room     ON app.messages(room_id, created_at);
CREATE INDEX IF NOT EXISTS idx_room_members_room ON app.room_members(room_id);

COMMENT ON TABLE app.rooms    IS 'Salas de conversación';
COMMENT ON TABLE app.messages IS 'Mensajes persistidos por sala (historial)';
