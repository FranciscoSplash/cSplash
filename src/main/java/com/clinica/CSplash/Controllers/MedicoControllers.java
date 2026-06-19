package com.clinica.CSplash.Controllers;


import com.clinica.CSplash.DTO.Request.MedicoRequest;
import com.clinica.CSplash.DTO.Response.MedicoResponse;
import com.clinica.CSplash.Service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/medicos")

public class MedicoControllers {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    @Operation(summary = "lista os Medicos", description = "Lista os Medicos do sistema")

    public ResponseEntity<Page<MedicoResponse>>listarMedico(@PageableDefault(size=5, sort="nome")Pageable paginacao){
        Page<MedicoResponse>listar=medicoService.listarMedico(paginacao);

        if(listar.isEmpty()){
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(listar,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "lista medicos por Id", description = "Lista Medicos do sistema por Id")

    public ResponseEntity<MedicoResponse>listarMedicoPorId(UUID id){
        return new ResponseEntity<>(medicoService.listarPorId(id),HttpStatus.OK);

    }
    @PostMapping
    @Operation(summary = "cria um medico", description = "Criar um medico sistema")

    public ResponseEntity<MedicoResponse>criarMedico(@RequestBody @Valid MedicoRequest medicoRequest){
        return new ResponseEntity<>(medicoService.cadastrar(medicoRequest),HttpStatus.CREATED);
    }
    @PutMapping
    @Operation(summary = "atualizar", description = "atualiza dados do medico no sistema")

    public ResponseEntity<MedicoResponse>atualizarMedico(@PathVariable UUID id, MedicoRequest medicoRequest){
        return new ResponseEntity<>(medicoService.atualizarMedico(id,medicoRequest), HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    @Operation(summary = " Apagar", description = "Apaga  os dados do medico no sistema")

    public ResponseEntity<MedicoResponse>apagarMedico(@PathVariable UUID id){

        medicoService.apagarMedico(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
