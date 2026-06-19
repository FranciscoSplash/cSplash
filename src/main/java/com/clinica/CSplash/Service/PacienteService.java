package com.clinica.CSplash.Service;


import com.clinica.CSplash.DTO.Request.PacienteRequest;
import com.clinica.CSplash.DTO.Response.PacienteResponse;
import com.clinica.CSplash.Model.Enum.Genero;
import com.clinica.CSplash.Model.Paciente;
import com.clinica.CSplash.Repository.IPacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PacienteService {

    @Autowired
    private IPacienteRepository pacienteRepository;


    public PacienteResponse criarPaciente(PacienteRequest request) {
        Paciente paciente = new Paciente();
        paciente.setNome(request.nome());
        paciente.setCpf(request.cpf());
        paciente.setTelefone(request.telefone());
        paciente.setEmail(request.email());
        paciente.setGenero(request.genero());


        return toResponse(pacienteRepository.save(paciente));
    }

    public Page<PacienteResponse> listarPaciente(Pageable paginacao) {
        return pacienteRepository.findAll(paginacao).map(this::toResponse);
    }

    public PacienteResponse listarPorId(UUID id) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id inexistente"));

        return toResponse(paciente);
    }

    public PacienteResponse atualizarDadosDoPaciente(UUID id, PacienteRequest pacienteRequest) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id inexistente"));

        paciente.setNome(pacienteRequest.nome());
        paciente.setEmail(pacienteRequest.email());
        paciente.setCpf(pacienteRequest.cpf());
        paciente.setTelefone(pacienteRequest.telefone());
        paciente.setGenero(pacienteRequest.genero());

        return toResponse(pacienteRepository.save(paciente));
    }

    public void apagarPaciente(UUID id) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id inexistente"));
        pacienteRepository.delete(paciente);
    }

    private PacienteResponse toResponse(Paciente paciente) {
        return new PacienteResponse(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getTelefone(),
                paciente.getGenero(),
                paciente.getEmail()


        );

    }
}
