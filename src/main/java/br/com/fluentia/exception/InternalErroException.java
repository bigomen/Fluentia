package br.com.fluentia.exception;

import java.io.Serial;

public class InternalErroException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public InternalErroException(){
        super("Erro interno no sistema, entre em contato com a equipe técnica!");
    }
}
