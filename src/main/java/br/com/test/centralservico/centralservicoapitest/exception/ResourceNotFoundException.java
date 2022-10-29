package br.com.test.centralservico.centralservicoapitest.exception;


public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 5964714084530113577L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}
