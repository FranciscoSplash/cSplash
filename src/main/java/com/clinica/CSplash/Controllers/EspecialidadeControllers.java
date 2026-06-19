package com.clinica.CSplash.Controllers;


import com.clinica.CSplash.DTO.Request.EspecialidadeRequest;
import com.clinica.CSplash.DTO.Response.EspecialidadeResponse;
import com.clinica.CSplash.Service.EspecialidadeService;
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
@RequestMapping("/api/especialidades")
public class EspecialidadeControllers {

    @Autowired
    private EspecialidadeService especialidadeService;

    @GetMapping
    public ResponseEntity<Page<EspecialidadeResponse>>listarEspecialidade(@PageableDefault(size=5, sort = "nomeDaEspecialidade") Pageable paginacao){
        Page<EspecialidadeResponse> listar =especialidadeService.listarEspecialidade(paginacao);

        if(listar.isEmpty()){
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(listar,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeResponse>listarPorId(@PathVariable UUID id){
        return new ResponseEntity<>(especialidadeService.listarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EspecialidadeResponse>criarEspecialidade(@RequestBody @Valid EspecialidadeRequest especialidadeRequest){
        return new ResponseEntity<>(especialidadeService.criarEspecialidade(especialidadeRequest), HttpStatus.CREATED);

    }
    @PutMapping("/{id}")
        public ResponseEntity<EspecialidadeResponse>atualizarEspecialidade(@PathVariable UUID id, @RequestBody EspecialidadeRequest especialidadeRequest){
        return  new ResponseEntity<>(especialidadeService.atualizarEspecialidade(id, especialidadeRequest), HttpStatus.OK);
        }
        @DeleteMapping("/{id}")
    public ResponseEntity<EspecialidadeResponse>apagarEspecialidade(@PathVariable  UUID id){
        especialidadeService.apgarEspecialidade(id);

        return new ResponseEntity<>(HttpStatus.OK);
        }
}
