package com.clinica.CSplash.Service;

import com.clinica.CSplash.DTO.Request.ConsultaRequest;
import com.clinica.CSplash.DTO.Request.PacienteRequest;
import com.clinica.CSplash.DTO.Response.ConsultaResponse;
import com.clinica.CSplash.DTO.Response.PacienteResponse;
import com.clinica.CSplash.Model.Consulta;
import com.clinica.CSplash.Model.Enum.Status;
import com.clinica.CSplash.Model.Medico;
import com.clinica.CSplash.Model.Paciente;
import com.clinica.CSplash.Repository.IConsultaRepository;
import com.clinica.CSplash.Repository.IMedicoRespository;
import com.clinica.CSplash.Repository.IPacienteRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class ConsultaService {

    @Autowired
    private IConsultaRepository consultaRepository;

    @Autowired
    private IMedicoRespository medicoRespository;

    @Autowired
    private IPacienteRepository pacienteRepository;

    public ConsultaResponse criarConsulta(ConsultaRequest request) {

        // Busca o Paciente e o Médico no banco de dados pelos IDs vindos do Request
        Medico medico = medicoRespository.findById(request.medicoId())
                .orElseThrow(() -> new EntityNotFoundException("Medico não encontrado"));
        Paciente paciente = pacienteRepository.findById(request.pacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente nao encontrado"));

        //VALIDAÇÃO: Verifica se o paciente já tem outra consulta no mesmo horário
        boolean medicoOcupado = consultaRepository.existsByMedicoIdAndDiaDaConsultaAndHoraDaConsulta(medico.getId(), request.diaDaConsulta(), request.horaDaConsulta());
        if (medicoOcupado) {
            throw new RuntimeException("Este medico ja tem consulta marcada");
        }
        boolean pacienteOcupado = consultaRepository.existsByPacienteIdAndDiaDaConsultaAndHoraDaConsulta(paciente.getId(), request.diaDaConsulta(), request.horaDaConsulta());
        if (pacienteOcupado) {
            throw new RuntimeException("Paciente ja tem consulta marcada");
        }
        java.time.LocalTime horaAbertura=java.time.LocalTime.of(8, 0,0);  //horario de abertura
        java.time.LocalTime horaFechamento=java.time.LocalTime.of(18,0,0 ); //horario de fechamento

        if(request.horaDaConsulta().isBefore(horaAbertura) || request.horaDaConsulta().isAfter(horaFechamento)){
            throw new RuntimeException("Horario Invalido....Por favor tente mais tarde");
        }
        DayOfWeek dia= request.diaDaConsulta().getDayOfWeek();
        if(dia==DayOfWeek.SATURDAY || dia==DayOfWeek.SUNDAY ){
            throw new RuntimeException("Não trabalhamos ao fins de semana, So Voltamos abrir na Segunda");
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setHoraDaConsulta(request.horaDaConsulta());
        consulta.setDiaDaConsulta(request.diaDaConsulta());
        consulta.setMedico(medico);
        consulta.setStatus(request.status());

        return toResponse(consultaRepository.save(consulta));
    }

    public Page<ConsultaResponse> listarconsulta(Pageable paginacao) {
        return consultaRepository.findAll(paginacao).map(this::toResponse);
    }

    public ConsultaResponse listarPorId(UUID id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id inexistente"));

        return toResponse(consulta);
    }

    public ConsultaResponse atualizarDadosDoPaciente(UUID id, ConsultaRequest consultaRequest) {

        Medico medico = medicoRespository.findById(consultaRequest.medicoId()).orElseThrow(() -> new EntityNotFoundException("Medico não encontrado"));
        Paciente paciente = pacienteRepository.findById(consultaRequest.pacienteId()).orElseThrow(() -> new EntityNotFoundException("Paciente nao encontrado"));

        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id inexistente"));

        consulta.setPaciente(paciente);
        consulta.setHoraDaConsulta(consultaRequest.horaDaConsulta());
        consulta.setDiaDaConsulta(consultaRequest.diaDaConsulta());
        consulta.setMedico(medico);
        consulta.setStatus(consultaRequest.status());


        return toResponse(consultaRepository.save(consulta));
    }

    public void apagarConsulta(UUID id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id inexistente"));

        consultaRepository.delete(consulta);
    }

    private ConsultaResponse toResponse(Consulta consulta) {
        return new ConsultaResponse(
                consulta.getId(),
                consulta.getPaciente(),
                consulta.getDiaDaConsulta(),
                consulta.getHoraDaConsulta(),
                consulta.getMedico(),
                consulta.getStatus()


        );
    }
}
