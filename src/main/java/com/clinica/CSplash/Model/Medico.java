package com.clinica.CSplash.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "nome")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;


    private String crm;


    private String telefone;
    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_especialidade")
    private Especialidade especialidade;



    @CreationTimestamp
    private LocalDate data;

}