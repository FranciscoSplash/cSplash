package com.clinica.CSplash.DTO.Request;

import com.clinica.CSplash.Model.Enum.Status;
import com.clinica.CSplash.Model.Medico;
import com.clinica.CSplash.Model.Paciente;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ConsultaRequest(
        UUID id,

        @NotNull(message = "O ID do paciente é obrigatório")
        UUID pacienteId,      // Tem que ser exatamente pacienteId


        @NotNull(message = "O ID do médico é obrigatório")
        UUID medicoId,


        @NotNull(message = "A data da consulta é obrigatória")
        @FutureOrPresent(message = "A data da consulta deve ser hoje ou uma data futura")
        java.time.LocalDate diaDaConsulta,

        @NotNull(message = "O horário da consulta é obrigatório")
        LocalTime horaDaConsulta,
        Status status
) {
}

