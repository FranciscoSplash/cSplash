package com.clinica.CSplash.Repository;

import com.clinica.CSplash.Model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface IConsultaRepository extends JpaRepository<Consulta, UUID> {

    // 1. Verifica se o MÉDICO já tem consulta nesse dia e horário

    boolean existsByMedicoIdAndDiaDaConsultaAndHoraDaConsulta(UUID medicoId, LocalDate dia, LocalTime hora);


//    Verifica se o PACIENTE já tem alguma consulta marcada nesse mesmo horário (evita ele estar em dois lugares ao mesmo tempo)


     boolean existsByPacienteIdAndDiaDaConsultaAndHoraDaConsulta(UUID pacienteId, LocalDate dia, LocalTime hora);

}