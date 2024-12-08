package com.bifrost.ChatApp.controller;

import com.bifrost.ChatApp.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar operaciones relacionadas con tokens.
 * Este controlador maneja solicitudes HTTP relacionadas con la generación de tokens.
 *
 * Utiliza el servicio {@link TokenService} para generar tokens JWT.
 *
 * Anotado con {@code @RestController} para indicar que es un controlador de Spring que
 * devuelve respuestas directamente en el cuerpo de HTTP en lugar de renderizar una vista.
 *
 * Los métodos dentro de esta clase están mapeados bajo la URL base "/api/token".
 * Soporta CORS con la anotación {@code @CrossOrigin}.
 *
 * Utiliza inyección de dependencias de Spring, como lo indica {@code @RequiredArgsConstructor},
 * para inicializar el servicio de token necesario.
 */
@RestController
@RequestMapping("/api/token")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j

public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/generateToken")
    @CrossOrigin
    public ResponseEntity<String> generateToken(@RequestParam String username) {
        log.info("INICIO---->generateToken :"+"username = " + username);
        try {
            String token = tokenService.generateToken(username); // Genera un token al registrar al usuario
            return ResponseEntity.ok()
                    .body(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
