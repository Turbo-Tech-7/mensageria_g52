package com.mensageria.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PublicarEventoRequest(

        @NotBlank(message = "tipo é obrigatório")
        String tipo,

        @NotNull(message = "payload é obrigatório")
        Object payload
) {
}
