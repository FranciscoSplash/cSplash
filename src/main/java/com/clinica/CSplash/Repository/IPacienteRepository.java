package com.clinica.CSplash.Repository;

import com.clinica.CSplash.Model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, UUID> {
}
