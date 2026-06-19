package com.clinica.CSplash.Service;

import com.clinica.CSplash.DTO.Request.MedicoRequest;
import com.clinica.CSplash.DTO.Response.MedicoResponse;
import com.clinica.CSplash.Model.Especialidade;
import com.clinica.CSplash.Model.Medico;
import com.clinica.CSplash.Repository.IMedicoRespository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class MedicoService {

    @Autowired
    private IMedicoRespository medicoRespository;

    //Crud

    //Criar

    public MedicoResponse cadastrar(MedicoRequest request){
        Medico medico =new Medico();
                medico.setNome(request.nome());
                medico.setCrm(request.crm());
                medico.setEmail(request.email());
                medico.setTelefone(request.telefone());
                medico.setEspecialidade(request.especilidade());


                return toResponse(medicoRespository.save(medico));

    }
    //Listar
public Page<MedicoResponse> listarMedico (Pageable paginacao){
        return medicoRespository.findAll(paginacao).map(this::toResponse);
}
//Listar Por id
public MedicoResponse listarPorId(UUID id){
        Medico medic = medicoRespository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Id inexistente"));

        return toResponse(medic);
}
//atualizar

public MedicoResponse atualizarMedico(UUID id, MedicoRequest medicoRequest){
        Medico medic = medicoRespository.findById(id).orElseThrow(()-> new EntityNotFoundException("id inexistente"));
        medic.setNome(medicoRequest.nome());
        medic.setEmail(medicoRequest.email());
        medic.setCrm(medicoRequest.crm());
        medic.setTelefone(medicoRequest.telefone());
        medic.setEspecialidade(medicoRequest.especilidade());

        return toResponse(medicoRespository.save(medic));
}

//Apagar
public void apagarMedico(UUID id){
        Medico medic=medicoRespository.findById(id).orElseThrow(()->new EntityNotFoundException("Id inexistente"));

        medicoRespository.delete(medic);
}
    private MedicoResponse toResponse(Medico medico){
     return new MedicoResponse(

            medico.getId(),
            medico.getNome(),
             medico.getEmail(),
            medico.getCrm(),
            medico.getTelefone(),
            medico.getEspecialidade()



    );
    }
}
