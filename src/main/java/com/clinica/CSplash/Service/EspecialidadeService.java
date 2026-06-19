package com.clinica.CSplash.Service;


import com.clinica.CSplash.DTO.Request.EspecialidadeRequest;
import com.clinica.CSplash.DTO.Response.EspecialidadeResponse;
import com.clinica.CSplash.Model.Especialidade;
import com.clinica.CSplash.Repository.IEspecialidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EspecialidadeService {

    @Autowired
    private IEspecialidadeRepository especialidadeRepository;


    public EspecialidadeResponse criarEspecialidade(EspecialidadeRequest request){
        Especialidade especialidade= new Especialidade();
        especialidade.setNomeDaEspecialidade(request.nomeDaEspecialidade());

        return toResponse(especialidadeRepository.save(especialidade));
    }

    public Page<EspecialidadeResponse> listarEspecialidade(Pageable paginacao){
        return especialidadeRepository.findAll(paginacao).map(this::toResponse);
    }
    public EspecialidadeResponse listarPorId(UUID id){
       Especialidade especialidade = especialidadeRepository.findById(id).orElseThrow(()->new EntityNotFoundException("ID inexistente"));

        return toResponse(especialidade);
    }
    public EspecialidadeResponse atualizarEspecialidade(UUID id, EspecialidadeRequest especialidadeRequest){
        Especialidade especialidade=especialidadeRepository.findById(id).orElseThrow(()->new EntityNotFoundException("ID inexistente"));

        especialidade.setNomeDaEspecialidade(especialidadeRequest.nomeDaEspecialidade());
        return toResponse(especialidadeRepository.save(especialidade));
    }

    public void apgarEspecialidade(UUID id){
        Especialidade especialidade=especialidadeRepository.findById(id).orElseThrow(()->new EntityNotFoundException("ID inexistente"));

        especialidadeRepository.delete(especialidade);
    }
    private EspecialidadeResponse toResponse(Especialidade especialidade){
        return new EspecialidadeResponse(
        especialidade.getId(),
        especialidade.getNomeDaEspecialidade()
        );
    }
}
