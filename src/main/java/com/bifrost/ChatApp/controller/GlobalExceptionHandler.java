package com.bifrost.ChatApp.controller;

import com.bifrost.ChatApp.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * GlobalExceptionHandler se encarga de manejar excepciones globalmente
 * en una aplicación Spring Boot. Proporciona un controlador centralizado
 * para excepciones lanzadas dentro de la aplicación.
 *
 * Este controlador define métodos para manejar distintas excepciones específicas
 * que heredan de RuntimeException, así como una excepción genérica para errores
 * inesperados. Cada método está anotado con @ExceptionHandler para especificar
 * la excepción que maneja y usa @ResponseStatus para devolver el código HTTP apropiado.
 *
 * Excepciones manejadas:
 *
 * - AccesoDenegadoException: Retorna un estado HTTP 403 FORBIDDEN.
 * - CredencialesInvalidasException: Retorna un estado HTTP 401 UNAUTHORIZED.
 * - OperacionNoPermitidaException: Retorna un estado HTTP 403 FORBIDDEN.
 * - UsuarioInactivoException: Retorna un estado HTTP 403 FORBIDDEN.
 * - UsuarioNoEncontradoException: Retorna un estado HTTP 404 NOT FOUND.
 * - UsuarioYaExisteException: Retorna un estado HTTP 409 CONFLICT.
 * - EmailYaExisteException: Retorna un estado HTTP 409 CONFLICT.
 * - Exception: Para cualquier otra excepción no prevista, se registra el
 *   error y se retorna un estado HTTP 500 INTERNAL SERVER ERROR con un
 *   mensaje de error genérico.
 */
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
