package com.clinica.CSplash.Model;


import com.clinica.CSplash.Model.Enum.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name="paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank()
    private String nome;

    @Column(unique = true)
    private String cpf;

    @NotBlank
    private String telefone;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(unique = true)
    private String email;

    @CreationTimestamp
    private LocalDate data;
}
