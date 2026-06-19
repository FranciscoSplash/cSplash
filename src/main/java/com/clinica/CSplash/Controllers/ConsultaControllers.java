package com.clinica.CSplash.Controllers;


import com.clinica.CSplash.DTO.Request.ConsultaRequest;
import com.clinica.CSplash.DTO.Response.ConsultaResponse;
import com.clinica.CSplash.DTO.Response.MedicoResponse;
import com.clinica.CSplash.Service.ConsultaService;
import com.clinica.CSplash.Service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.hibernate.engine.jdbc.Size;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/consultas")
public class ConsultaControllers {

    @Autowired
    private ConsultaService consultaService;


    @GetMapping
    @Operation(summary = "lista Consulta", description = "Lista consulta do sistema")

    public ResponseEntity<Page<ConsultaResponse>> listartodasConsultas(
            @PageableDefault(size = 5, sort = "diaDaConsulta") Pageable paginacao
    ) {


        Page<ConsultaResponse> listar = consultaService.listarconsulta(paginacao);

        if (listar.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listar, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    @Operation(summary = "Lista a Consulta de um ID", description = "Lista uma consulta do sistema por Id")

    public ResponseEntity<ConsultaResponse>listarConsultasPorId(@PathVariable UUID id){
        return  new ResponseEntity<>(consultaService.listarPorId(id), HttpStatus.OK);
    }
    @PostMapping
    @Operation(summary = "Cria Consulta", description = "Cria uma consulta do sistema")

    public ResponseEntity<ConsultaResponse>criarConsultas(@RequestBody @Valid ConsultaRequest consultaRequest){
        return new ResponseEntity<>(consultaService.criarConsulta(consultaRequest),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza Consulta", description = "Atualiza uma consulta do sistema")

    public ResponseEntity<ConsultaResponse>atualizarConsulta(@PathVariable UUID id, ConsultaRequest consultaRequest){
        return  new ResponseEntity<>(consultaService.atualizarDadosDoPaciente(id, consultaRequest), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Consulta", description = "Remove uma consulta do sistema")
      public ResponseEntity<ConsultaResponse>apagarConsulta(@PathVariable UUID id){
        consultaService.apagarConsulta(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
