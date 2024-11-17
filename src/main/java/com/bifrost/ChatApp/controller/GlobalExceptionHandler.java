package com.bifrost.ChatApp.controller;

import com.bifrost.ChatApp.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AccesoDenegadoException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public String handleAccesoDenegadoException(AccesoDenegadoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public String handleCredencialesInvalidasException(CredencialesInvalidasException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(OperacionNoPermitidaException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public String handleOperacionNoPermitidaException(OperacionNoPermitidaException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UsuarioInactivoException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public String handleUsuarioInactivoException(UsuarioInactivoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public String handleUsuarioNoEncontradoException(UsuarioNoEncontradoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UsuarioYaExisteException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    public String handleUsuarioYaExisteException(UsuarioYaExisteException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EmailYaExisteException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    public String handleEmailYaExisteException(EmailYaExisteException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
