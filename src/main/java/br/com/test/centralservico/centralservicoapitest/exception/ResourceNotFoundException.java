package br.com.test.centralservico.centralservicoapitest.exception;


public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}
