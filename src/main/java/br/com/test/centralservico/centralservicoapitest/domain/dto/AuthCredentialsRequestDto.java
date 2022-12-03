package br.com.test.centralservico.centralservicoapitest.domain.dto;

import lombok.Data;

@Data
public class AuthCredentialsRequestDto {

    private String username;
    private String password;

}
