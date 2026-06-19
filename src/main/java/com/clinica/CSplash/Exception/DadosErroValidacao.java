package com.clinica.CSplash.Exception;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record DadosErroValidacao(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mens

) {
}
