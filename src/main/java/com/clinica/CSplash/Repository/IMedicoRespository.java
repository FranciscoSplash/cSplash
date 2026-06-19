package com.clinica.CSplash.Repository;

import com.clinica.CSplash.Model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IMedicoRespository extends JpaRepository<Medico, UUID> {
}
