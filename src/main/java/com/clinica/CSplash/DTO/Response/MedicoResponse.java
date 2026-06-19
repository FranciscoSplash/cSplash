package com.clinica.CSplash.DTO.Response;

import com.clinica.CSplash.Model.Especialidade;

import java.util.UUID;

public record MedicoResponse(
        UUID id,
        String nome,
        String email,
        String crm,
        String telefone,
        Especialidade especilidade
) {
}
