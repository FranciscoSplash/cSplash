package com.clinica.CSplash.DTO.Request;

import com.clinica.CSplash.Model.Enum.Cargo;

import java.util.UUID;

public record CadastroRequest(
        UUID id,
        String nome,
        String email,
        String senha,
        Cargo cargo
) {
}
