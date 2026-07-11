package com.ar.laboratory.realtimegateway.chat.infrastructure.inbound.web.dto;

import com.ar.laboratory.realtimegateway.chat.domain.model.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Alta de sala. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    private String name;

    /** Tipo de sala (PUBLIC, PRIVATE, DIRECT). Opcional; por defecto PUBLIC. */
    private RoomType type;
}
