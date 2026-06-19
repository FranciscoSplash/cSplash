package com.clinica.CSplash.DTO.Request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record EspecialidadeRequest(
        UUID id,
        @NotBlank(message = "Campo Obrigatorio")
        String nomeDaEspecialidade
) {
}
