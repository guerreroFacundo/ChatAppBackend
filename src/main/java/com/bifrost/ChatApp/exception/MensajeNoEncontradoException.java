package com.bifrost.ChatApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MensajeNoEncontradoException extends RuntimeException {
    public MensajeNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
