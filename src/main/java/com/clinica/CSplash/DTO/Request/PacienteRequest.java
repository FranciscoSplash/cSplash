package com.clinica.CSplash.DTO.Request;

import com.clinica.CSplash.Model.Enum.Genero;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record PacienteRequest(
       UUID id,
       @NotBlank(message = "O campo Nome deve ser obrigatorio")
       String nome,

       @NotBlank(message = "Campo é obrigatorio")
       @Pattern(regexp = "\\d{11}", message = "O cpf deve ter 11 digitos")
       String cpf,

       @NotBlank(message = "Campo é obrigatorio")
       String telefone,

        @NotNull
       Genero genero,


       @NotBlank(message = "Email é obrigatorio")
       @Email(message = "Formato do Email invalido")
       String email
) {
}
