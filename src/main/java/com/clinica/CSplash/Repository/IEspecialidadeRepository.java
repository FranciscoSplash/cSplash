package com.clinica.CSplash.Repository;

import com.clinica.CSplash.Model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IEspecialidadeRepository extends JpaRepository<Especialidade, UUID> {
}
