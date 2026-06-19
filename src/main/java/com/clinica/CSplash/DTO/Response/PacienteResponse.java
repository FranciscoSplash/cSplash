package com.clinica.CSplash.DTO.Response;

import com.clinica.CSplash.Model.Enum.Genero;

import java.util.UUID;

public record PacienteResponse(
        UUID id,
        String nome,
        String cpf,
        String telefone,
        Genero genero,
        String email
) {
}
