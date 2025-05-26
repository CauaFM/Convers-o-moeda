package com.backend.conversao.exception;

public class MoedaNotFoundException extends RuntimeException {
    public MoedaNotFoundException(String identificador) {
        super("Moeda não encontrada: " + identificador);
    }
}
