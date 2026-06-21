package com.clinica.CSplash.Repository;

import com.clinica.CSplash.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, UUID> {

    UserDetails findByEmail(String email);




}
