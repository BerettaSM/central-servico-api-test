package br.com.test.centralservico.centralservicoapitest.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {

    private Integer status;
    private LocalDateTime timestamp;
    private String message;


}
