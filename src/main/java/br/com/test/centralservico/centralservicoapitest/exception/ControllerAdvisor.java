package br.com.test.centralservico.centralservicoapitest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorMessage.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .timestamp(LocalDateTime.now())
                            .build()
        );

    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidPathVariable(NumberFormatException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorMessage.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Parâmetro inválido. Esperado: int.")
                            .timestamp(LocalDateTime.now())
                            .build()
        );

    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> sqlIntegrityConstraintViolation(SQLIntegrityConstraintViolationException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorMessage.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .timestamp(LocalDateTime.now())
                            .build()
        );

    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> illegalArgument(IllegalArgumentException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorMessage.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .timestamp(LocalDateTime.now())
                            .build()
        );

    }

}
