package com.bifrost.ChatApp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsuarioYaExisteException extends RuntimeException {
    public UsuarioYaExisteException(String mensaje) {
        super(mensaje);
    }
}
