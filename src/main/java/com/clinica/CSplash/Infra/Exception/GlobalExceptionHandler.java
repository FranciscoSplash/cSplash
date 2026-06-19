package com.clinica.CSplash.Infra.Exception;

import com.clinica.CSplash.Exception.DadosErroValidacao;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //  CAPTURA ERROS DE ID NÃO ENCONTRADO (Muda de Status 500 para 404 Not Found)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DadosErroValidacao>tratarErros404(EntityNotFoundException e){
        DadosErroValidacao erro=new DadosErroValidacao(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso nao Encontrado",
                e.getMessage()     // Exibe a mensagem que você colocou no .orElseThrow()
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }
    // 2. CAPTURA ERROS DE REGRAS DE NEGÓCIO (Muda de Status 500 para 400 Bad Request)
    // Captura quando o médico ou paciente já estão ocupados (RuntimeException)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DadosErroValidacao> tratarErros400(RuntimeException e){
        DadosErroValidacao erro =new DadosErroValidacao(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Regra violada",    //xibe "Este medico ja tem consulta marcada", por exemplo.
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<java.util.Map<String,String>>tratarValidacao(org.springframework.web.bind.MethodArgumentNotValidException e){
        java.util.Map<String, String> erro= new java.util.HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            erro.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(erro);
    }
}
