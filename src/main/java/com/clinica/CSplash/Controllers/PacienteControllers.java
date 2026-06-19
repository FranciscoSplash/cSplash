package com.clinica.CSplash.Controllers;

import com.clinica.CSplash.DTO.Request.PacienteRequest;
import com.clinica.CSplash.DTO.Response.PacienteResponse;
import com.clinica.CSplash.Service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/pacientes")
public class PacienteControllers {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    @Operation(summary = "Lista todos os pacientes", description = "Retorna uma lista com todos os pacientes cadastrados")
    public ResponseEntity<Page<PacienteResponse>> listarTodos(@PageableDefault(size=7, sort="nome")Pageable paginacao) {
        Page<PacienteResponse> pacientes = pacienteService.listarPaciente(paginacao);
        if (pacientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca paciente por ID", description = "Retorna os dados de um paciente específico através do UUID")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable UUID id) {
        return new ResponseEntity<>(pacienteService.listarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo paciente", description = "Salva um paciente no banco de dados")
    public ResponseEntity<PacienteResponse> cadastrar(@RequestBody @Valid PacienteRequest request) {
        return new ResponseEntity<>(pacienteService.criarPaciente(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza dados do paciente", description = "Modifica as informações de um paciente existente pelo ID")
    public ResponseEntity<PacienteResponse> atualizar(@PathVariable UUID id, @RequestBody @Valid PacienteRequest request) {
        return new ResponseEntity<>(pacienteService.atualizarDadosDoPaciente(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um paciente", description = "Remove o registro de um paciente do sistema pelo ID")
    public ResponseEntity<Void> apagar(@PathVariable UUID id) {
        pacienteService.apagarPaciente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content para deleção com sucesso
    }
}