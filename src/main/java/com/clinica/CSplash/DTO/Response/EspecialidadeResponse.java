package com.clinica.CSplash.DTO.Response;

import java.util.UUID;

public record EspecialidadeResponse(
        UUID id,
        String nomeDaEspecialidade
) {
}
