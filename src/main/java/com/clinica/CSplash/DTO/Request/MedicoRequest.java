package com.clinica.CSplash.DTO.Request;

import com.clinica.CSplash.Model.Especialidade;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MedicoRequest(
        UUID id,
        @NotBlank(message = "O campo Nome deve ser obrigatorio")
        String nome,
        @NotBlank(message = "Email é obrigatorio")
        @Email(message = "Formato do Email invalido")
        String email,

        @NotBlank(message = "Campo obrigatorio")
        String crm,
        @NotBlank(message="Campo obrigatorio")
        String telefone,

        @NotNull
        Especialidade especilidade
) {
}
