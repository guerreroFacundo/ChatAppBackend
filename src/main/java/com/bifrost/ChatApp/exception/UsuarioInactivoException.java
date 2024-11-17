package com.bifrost.ChatApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UsuarioInactivoException extends RuntimeException {
    public UsuarioInactivoException(String mensaje) {
        super(mensaje);
    }
}
