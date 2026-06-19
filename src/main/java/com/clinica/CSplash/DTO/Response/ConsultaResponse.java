package com.clinica.CSplash.DTO.Response;

import com.clinica.CSplash.Model.Enum.Status;
import com.clinica.CSplash.Model.Medico;
import com.clinica.CSplash.Model.Paciente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ConsultaResponse(
        UUID id,
        Paciente paciente,
        java.time.LocalDate diaDaConsulta,
        LocalTime horaDaConsulta,
        Medico medico,
       Status status





) {
}
